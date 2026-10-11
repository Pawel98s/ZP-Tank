package pl.pawlo.zptank.api.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.order.CreateOrderDTO;
import pl.pawlo.zptank.api.dto.order.OrderDTO;
import pl.pawlo.zptank.api.dto.order.UpdateOrderStatusDTO;
import pl.pawlo.zptank.api.mapper.OrderMapper;
import pl.pawlo.zptank.domain.OrderStatus;
import pl.pawlo.zptank.domain.order.Order;
import pl.pawlo.zptank.service.OrderService;

import java.util.List;
import java.util.Comparator;
import java.time.LocalDate;
import pl.pawlo.zptank.api.dto.PageResponse;
import java.util.Locale;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderDTO createOrder(@RequestBody CreateOrderDTO orderDTO) {
        Order order = orderService.create(
                orderDTO.getOrderRequestId(),
                orderDTO.getIntermediaryId(),
                orderDTO.getDeliveryAddressId());

        return orderMapper.mapToDTO(order);
    }

    @GetMapping("/{id}")
    public OrderDTO findById(@PathVariable Long id) {
        Order order = orderService.findById(id);
        return orderMapper.mapToDTO(order);
    }

    @GetMapping()
    public List<OrderDTO> findAll(@RequestParam(required = false) OrderStatus status) {
        List<Order> orders;

        if (status != null) {
            orders = orderService.findByStatus(status);
        } else {
            orders = orderService.findAll();
        }

        return orders.stream()
                .map(orderMapper::mapToDTO)
                .toList();
    }

    @GetMapping("/search")
    public PageResponse<OrderDTO> search(@RequestParam(required = false) OrderStatus status,
                                             @RequestParam(required = false) Long clientId,
                                             @RequestParam(required = false) String waybill,
                                             @RequestParam(required = false) String text,
                                             @RequestParam(required = false) LocalDate from,
                                             @RequestParam(required = false) LocalDate to,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size,
                                             @RequestParam(defaultValue = "executionDate,asc") String sort) {
            return PageResponse.of(orderService.search(status, clientId, waybill, text, from, to,
                    PageRequest.of(Math.max(0, page), Math.max(1, size), orderSort(sort)))
                    .map(orderMapper::mapToDTO));
    }

    private Sort orderSort(String sort) {
        String[] parts = sort == null ? new String[0] : sort.split(",", 2);
        String field = parts.length == 0 ? "executionDate" : parts[0].trim();
        boolean desc = parts.length > 1 && "desc".equalsIgnoreCase(parts[1].trim());
        String property = switch (field) {
            case "id", "status", "createdAt", "plannedDeliveryDate", "deliveryDate", "executionDate" -> field;
            default -> "executionDate";
        };
        return Sort.by(desc ? Sort.Direction.DESC : Sort.Direction.ASC, property);
    }

    private boolean matchesText(Order order, String text) {
        if (text == null || text.isBlank()) return true;
        String query = text.toLowerCase(Locale.ROOT);
        String waybill = order.getWaybill() == null ? "" : order.getWaybill().getWaybillNumber();
        String client = order.getOrderRequest() == null || order.getOrderRequest().getClient() == null
                ? "" : order.getOrderRequest().getClient().getName();
        return (waybill != null && waybill.toLowerCase(Locale.ROOT).contains(query))
                || (client != null && client.toLowerCase(Locale.ROOT).contains(query));
    }
    @PatchMapping("/{id}")
    public OrderDTO updateOrder(@PathVariable Long id,
                                @RequestBody OrderDTO orderDTO) {
        Order order = orderMapper.mapToDomain(orderDTO);
        Order update = orderService.update(id, order);
        return orderMapper.mapToDTO(update);
    }

    @PatchMapping("/{id}/status")
    public OrderDTO updateStatus(@PathVariable Long id,
                                 @RequestBody UpdateOrderStatusDTO statusDTO) {
        Order order = orderService.updateStatus(id, statusDTO.getOrderStatus());
        return orderMapper.mapToDTO(order);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.deleteById(id);
    }
}

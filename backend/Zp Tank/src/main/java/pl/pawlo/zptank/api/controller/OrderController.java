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

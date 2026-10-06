package pl.pawlo.zptank.api.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.order.CreateOrderDTO;
import pl.pawlo.zptank.api.dto.order.OrderDTO;
import pl.pawlo.zptank.api.mapper.OrderMapper;
import pl.pawlo.zptank.domain.order.Order;
import pl.pawlo.zptank.service.OrderService;

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


}

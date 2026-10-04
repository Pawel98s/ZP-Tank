package pl.pawlo.zptank.api.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.order.OrderRequestDTO;
import pl.pawlo.zptank.api.dto.order.UpdateOrderRequestStatusDTO;
import pl.pawlo.zptank.api.mapper.OrderRequestMapper;
import pl.pawlo.zptank.domain.OrderRequestStatus;
import pl.pawlo.zptank.domain.order.OrderRequest;
import pl.pawlo.zptank.service.OrderRequestService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/order-requests")
public class OrderRequestController {

    private final OrderRequestService orderRequestService;
    private final OrderRequestMapper orderRequestMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderRequestDTO save(@RequestBody OrderRequestDTO orderRequestDTO) {
        OrderRequest save = orderRequestService.save(orderRequestMapper.mapToDomain(orderRequestDTO));
        return orderRequestMapper.mapToDTO(save);
    }

    @GetMapping("/{id}")
    public OrderRequestDTO findById(@PathVariable Long id) {
        OrderRequest orderRequest = orderRequestService.findById(id);
        return orderRequestMapper.mapToDTO(orderRequest);
    }

    @GetMapping
    public List<OrderRequestDTO> findAll(@RequestParam(required = false) OrderRequestStatus status) {

        List<OrderRequest> orderRequests;

        if (status != null) {
            orderRequests = orderRequestService.findByStatus(status);
        } else {
            orderRequests = orderRequestService.findAll();
        }
        return orderRequests.stream()
                .map(orderRequestMapper::mapToDTO)
                .toList();

    }

    @PatchMapping("/{id}")
    public OrderRequestDTO update(@PathVariable Long id,
                                  @RequestBody OrderRequestDTO orderRequestDTO) {
        OrderRequest orderRequest = orderRequestMapper.mapToDomain(orderRequestDTO);
        OrderRequest update = orderRequestService.update(id, orderRequest);
        return orderRequestMapper.mapToDTO(update);
    }

    @PatchMapping("{id}/status")
    public OrderRequestDTO updateStatus(@PathVariable Long id,
                                        @RequestBody UpdateOrderRequestStatusDTO request){

        OrderRequest orderRequest = orderRequestService.updateStatus(id, request.getOrderRequestStatus());
        return  orderRequestMapper.mapToDTO(orderRequest);
    }

    @DeleteMapping("/{id}")
    public void  delete(@PathVariable Long id) {
        orderRequestService.delete(id);
    }


}

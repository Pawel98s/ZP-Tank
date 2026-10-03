package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.zptank.domain.OrderRequestStatus;
import pl.pawlo.zptank.domain.order.OrderRequest;
import pl.pawlo.zptank.service.dao.OrderRequestDAO;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderRequestService {

    private final OrderRequestDAO orderRequestDAO;

    public OrderRequest save(OrderRequest orderRequest){
        return orderRequestDAO.save(orderRequest);
    }

    public OrderRequest findById(Long id){
        return orderRequestDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderRequest not found"));
    }


    public List<OrderRequest> findAll(){
        return  orderRequestDAO.findAll();
    }

    public void delete(Long id){
        OrderRequest byId = findById(id);
        orderRequestDAO.delete(byId.getId());
    }

    public OrderRequest update(Long id, OrderRequest orderRequest){

        OrderRequest existingOrderRequest = findById(id);
        OrderRequest updatedOrderRequest = OrderRequest.builder()
                .id(existingOrderRequest.getId())
                .status(orderRequest.getStatus() != null
                        ? orderRequest.getStatus()
                        : existingOrderRequest.getStatus())
                .client(orderRequest.getClient() != null
                        ? orderRequest.getClient()
                        : existingOrderRequest.getClient())
                .product(orderRequest.getProduct() != null
                        ? orderRequest.getProduct()
                        : existingOrderRequest.getProduct())
                .quantity(orderRequest.getQuantity() != null
                        ? orderRequest.getQuantity()
                        : existingOrderRequest.getQuantity())
                .price(orderRequest.getPrice() != null
                        ? orderRequest.getPrice()
                        : existingOrderRequest.getPrice())
                .executionDate(orderRequest.getExecutionDate() != null
                        ? orderRequest.getExecutionDate()
                        : existingOrderRequest.getExecutionDate())
                .notes(orderRequest.getNotes() != null
                        ? orderRequest.getNotes()
                        : existingOrderRequest.getNotes())
                .order(orderRequest.getOrder() != null
                        ? orderRequest.getOrder()
                        : existingOrderRequest.getOrder())
                .build();

        return orderRequestDAO.update(updatedOrderRequest);
    }
    public OrderRequest updateStatus(Long id, OrderRequestStatus status) {

        OrderRequest existingOrderRequest = findById(id);

        OrderRequest updatedOrderRequest = OrderRequest.builder()
                .id(existingOrderRequest.getId())
                .status(status)
                .createdAt(existingOrderRequest.getCreatedAt())
                .client(existingOrderRequest.getClient())
                .product(existingOrderRequest.getProduct())
                .quantity(existingOrderRequest.getQuantity())
                .price(existingOrderRequest.getPrice())
                .executionDate(existingOrderRequest.getExecutionDate())
                .notes(existingOrderRequest.getNotes())
                .order(existingOrderRequest.getOrder())
                .build();

        return orderRequestDAO.update(updatedOrderRequest);
    }

}

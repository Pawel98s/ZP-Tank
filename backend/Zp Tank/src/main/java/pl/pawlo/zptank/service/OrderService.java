package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawlo.zptank.domain.OrderStatus;
import pl.pawlo.zptank.domain.address.Address;
import pl.pawlo.zptank.domain.address.DeliveryAddress;
import pl.pawlo.zptank.domain.client.Client;
import pl.pawlo.zptank.domain.intermediary.Intermediary;
import pl.pawlo.zptank.domain.order.Order;
import pl.pawlo.zptank.domain.order.OrderRequest;
import pl.pawlo.zptank.domain.order.Waybill;
import pl.pawlo.zptank.domain.transport.Transport;
import pl.pawlo.zptank.service.dao.OrderDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderDAO orderDAO;
    private final OrderRequestService orderRequestService;
    private final IntermediaryService intermediaryService;
    private final DeliveryAddressService deliveryAddressService;
    private final WaybillService waybillService;

    public Order save(Order order) {
       return orderDAO.save(order);
    }

    public Order findById(Long id){
      return orderDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
    }

    public List<Order> findAll(){
        return orderDAO.findAll();
    }

    public void deleteById(Long id){
        Order order = findById(id);
        orderDAO.delete(order.getId());
    }

    public Order create(Long orderRequestId, Long intermediaryId, Long deliveryAddressId) {

        OrderRequest orderRequest = orderRequestService.findById(orderRequestId);


        Intermediary intermediary = intermediaryService.findById(intermediaryId);


        DeliveryAddress deliveryAddress;

        if (deliveryAddressId != null) {


            deliveryAddress = deliveryAddressService.findById(deliveryAddressId);

        } else {

            Client client = orderRequest.getClient();

            Address companyAddress = client.getCompanyAddress();

            deliveryAddress = deliveryAddressService.save(
                    DeliveryAddress.builder()
                            .city(companyAddress.getCity())
                            .postalCode(companyAddress.getPostalCode())
                            .street(companyAddress.getStreet())
                            .houseNumber(companyAddress.getHouseNumber())
                            .client(client)
                            .build()
            );
        }

        Waybill waybill = waybillService.generate();

        Order order = Order.builder()
                .status(OrderStatus.NEW)
                .orderRequest(orderRequest)
                .createdAt(LocalDateTime.now())
                .executionDate(orderRequest.getExecutionDate())
                .intermediary(intermediary)
                .waybill(waybill)
                .deliveryAddress(deliveryAddress)
                .build();

        return orderDAO.save(order);
    }

    @Transactional
    public Order update(Long id, Order order) {

        Order existingOrder = findById(id);

        Order updatedOrder = Order.builder()
                .id(existingOrder.getId())
                .status(existingOrder.getStatus())
                .orderRequest(existingOrder.getOrderRequest())
                .createdAt(existingOrder.getCreatedAt())
                .executionDate(
                        order.getExecutionDate() != null
                                ? order.getExecutionDate()
                                : existingOrder.getExecutionDate()
                )
                .plannedDeliveryDate(
                        order.getPlannedDeliveryDate() != null
                                ? order.getPlannedDeliveryDate()
                                : existingOrder.getPlannedDeliveryDate()
                )
                .deliveryDate(
                        order.getDeliveryDate() != null
                                ? order.getDeliveryDate()
                                : existingOrder.getDeliveryDate()
                )
                .intermediary(
                        order.getIntermediary() != null
                                ? order.getIntermediary()
                                : existingOrder.getIntermediary()
                )
                .waybill(existingOrder.getWaybill())
                .deliveryAddress(
                        order.getDeliveryAddress() != null
                                ? order.getDeliveryAddress()
                                : existingOrder.getDeliveryAddress()
                )
                .transport(
                        order.getTransport() != null
                                ? order.getTransport()
                                : existingOrder.getTransport()
                )
                .build();

        return orderDAO.update(updatedOrder);
    }

    public Order assignTransport(Order order, Transport transport) {
        Order assignedOrder = Order.builder()
                .id(order.getId())
                .status(order.getStatus())
                .orderRequest(order.getOrderRequest())
                .createdAt(order.getCreatedAt())
                .executionDate(order.getExecutionDate())
                .plannedDeliveryDate(order.getPlannedDeliveryDate())
                .deliveryDate(order.getDeliveryDate())
                .intermediary(order.getIntermediary())
                .waybill(order.getWaybill())
                .deliveryAddress(order.getDeliveryAddress())
                .transport(transport)
                .build();

        return orderDAO.update(assignedOrder);
    }

    @Transactional
    public Order updateStatus(Long id, OrderStatus status) {

        Order existingOrder = findById(id);

        Order updatedOrder = Order.builder()
                .id(existingOrder.getId())
                .status(status)
                .orderRequest(existingOrder.getOrderRequest())
                .createdAt(existingOrder.getCreatedAt())
                .executionDate(existingOrder.getExecutionDate())
                .plannedDeliveryDate(existingOrder.getPlannedDeliveryDate())
                .deliveryDate(existingOrder.getDeliveryDate())
                .intermediary(existingOrder.getIntermediary())
                .waybill(existingOrder.getWaybill())
                .deliveryAddress(existingOrder.getDeliveryAddress())
                .transport(existingOrder.getTransport())
                .build();

        return orderDAO.update(updatedOrder);
    }

    public List<Order> findByStatus(OrderStatus status) {
        return orderDAO.findByStatus(status);
    }

    public List<Order> findByClientId(Long clientId) {
        return orderDAO.findByClientId(clientId);
    }

    public List<Order> findByIntermediaryId(Long intermediaryId) {
        return orderDAO.findByIntermediaryId(intermediaryId);
    }

    public List<Order> findByTransportId(Long transportId) {
        return orderDAO.findByTransportId(transportId);
    }

    public List<Order> findWithoutTransport() {
        return orderDAO.findWithoutTransport();
    }

    public List<Order> findByExecutionDate(LocalDate date) {
        return orderDAO.findByExecutionDate(date);
    }

    public List<Order> findByExecutionDateBetween(LocalDate from, LocalDate to) {
        return orderDAO.findByExecutionDateBetween(from, to);
    }

}

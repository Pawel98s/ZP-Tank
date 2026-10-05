package pl.pawlo.zptank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.OrderStatus;
import pl.pawlo.zptank.domain.address.Address;
import pl.pawlo.zptank.domain.address.DeliveryAddress;
import pl.pawlo.zptank.domain.client.Client;
import pl.pawlo.zptank.domain.intermediary.Intermediary;
import pl.pawlo.zptank.domain.order.Order;
import pl.pawlo.zptank.domain.order.OrderRequest;
import pl.pawlo.zptank.domain.order.Waybill;
import pl.pawlo.zptank.service.dao.OrderDAO;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private OrderRequestService orderRequestService;

    @Mock
    private IntermediaryService intermediaryService;

    @Mock
    private DeliveryAddressService deliveryAddressService;

    @Mock
    private WaybillService waybillService;

    @InjectMocks
    private OrderService orderService;


    @Test
    void shouldCreateOrderWithExistingDeliveryAddress() {

        Long orderRequestId = 1L;
        Long intermediaryId = 2L;
        Long deliveryAddressId = 3L;

        Client client = Client.builder()
                .id(10L)
                .build();

        OrderRequest orderRequest = OrderRequest.builder()
                .id(orderRequestId)
                .client(client)
                .executionDate(LocalDate.of(2026, 10, 10))
                .build();

        Intermediary intermediary = Intermediary.builder()
                .id(intermediaryId)
                .name("Intermediary")
                .build();

        DeliveryAddress deliveryAddress = DeliveryAddress.builder()
                .id(deliveryAddressId)
                .city("Rzeszów")
                .street("Długa")
                .houseNumber("10")
                .build();

        Waybill waybill = Waybill.builder()
                .id(20L)
                .waybillNumber("1/10/2026")
                .build();

        Order savedOrder = Order.builder()
                .id(100L)
                .status(OrderStatus.NEW)
                .orderRequest(orderRequest)
                .intermediary(intermediary)
                .deliveryAddress(deliveryAddress)
                .waybill(waybill)
                .build();

        when(orderRequestService.findById(orderRequestId))
                .thenReturn(orderRequest);

        when(intermediaryService.findById(intermediaryId))
                .thenReturn(intermediary);

        when(deliveryAddressService.findById(deliveryAddressId))
                .thenReturn(deliveryAddress);

        when(waybillService.generate())
                .thenReturn(waybill);

        when(orderDAO.save(any(Order.class)))
                .thenReturn(savedOrder);

        Order result = orderService.create(
                orderRequestId,
                intermediaryId,
                deliveryAddressId
        );


        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals(OrderStatus.NEW, result.getStatus());
        assertEquals(orderRequest, result.getOrderRequest());
        assertEquals(intermediary, result.getIntermediary());
        assertEquals(deliveryAddress, result.getDeliveryAddress());
        assertEquals(waybill, result.getWaybill());

        verify(orderRequestService).findById(orderRequestId);
        verify(intermediaryService).findById(intermediaryId);
        verify(deliveryAddressService).findById(deliveryAddressId);
        verify(waybillService).generate();
        verify(orderDAO).save(any(Order.class));

        verify(deliveryAddressService, never())
                .save(any(DeliveryAddress.class));
    }


    @Test
    void shouldCreateDeliveryAddressFromCompanyAddressWhenDeliveryAddressIdIsNull() {


        Long orderRequestId = 1L;
        Long intermediaryId = 2L;

        Address companyAddress = Address.builder()
                .id(50L)
                .city("Rzeszów")
                .postalCode("35-001")
                .street("Długa")
                .houseNumber("10")
                .build();

        Client client = Client.builder()
                .id(10L)
                .companyAddress(companyAddress)
                .build();

        OrderRequest orderRequest = OrderRequest.builder()
                .id(orderRequestId)
                .client(client)
                .executionDate(LocalDate.of(2026, 10, 10))
                .build();

        Intermediary intermediary = Intermediary.builder()
                .id(intermediaryId)
                .name("Intermediary")
                .build();

        DeliveryAddress createdDeliveryAddress = DeliveryAddress.builder()
                .id(100L)
                .city("Rzeszów")
                .postalCode("35-001")
                .street("Długa")
                .houseNumber("10")
                .client(client)
                .build();

        Waybill waybill = Waybill.builder()
                .id(20L)
                .waybillNumber("1/10/2026")
                .build();

        Order savedOrder = Order.builder()
                .id(200L)
                .status(OrderStatus.NEW)
                .orderRequest(orderRequest)
                .intermediary(intermediary)
                .deliveryAddress(createdDeliveryAddress)
                .waybill(waybill)
                .build();

        when(orderRequestService.findById(orderRequestId))
                .thenReturn(orderRequest);

        when(intermediaryService.findById(intermediaryId))
                .thenReturn(intermediary);

        when(deliveryAddressService.save(any(DeliveryAddress.class)))
                .thenReturn(createdDeliveryAddress);

        when(waybillService.generate())
                .thenReturn(waybill);

        when(orderDAO.save(any(Order.class)))
                .thenReturn(savedOrder);


        Order result = orderService.create(
                orderRequestId,
                intermediaryId,
                null
        );


        assertNotNull(result);
        assertEquals(200L, result.getId());
        assertEquals(OrderStatus.NEW, result.getStatus());
        assertEquals(orderRequest, result.getOrderRequest());
        assertEquals(intermediary, result.getIntermediary());
        assertEquals(createdDeliveryAddress, result.getDeliveryAddress());
        assertEquals(waybill, result.getWaybill());

        verify(orderRequestService).findById(orderRequestId);
        verify(intermediaryService).findById(intermediaryId);
        verify(deliveryAddressService).save(any(DeliveryAddress.class));
        verify(waybillService).generate();
        verify(orderDAO).save(any(Order.class));

        verify(deliveryAddressService, never())
                .findById(anyLong());
    }

    @Test
    void shouldFindOrdersByStatus() {


        Order order1 = Order.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .status(OrderStatus.NEW)
                .build();

        List<Order> orders = List.of(order1, order2);

        when(orderDAO.findByStatus(OrderStatus.NEW))
                .thenReturn(orders);


        List<Order> result =
                orderService.findByStatus(OrderStatus.NEW);


        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(orders, result);

        verify(orderDAO).findByStatus(OrderStatus.NEW);
    }

    @Test
    void shouldFindOrdersByClientId() {

        Long clientId = 10L;

        Order order1 = Order.builder()
                .id(1L)
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .build();

        List<Order> orders = List.of(order1, order2);

        when(orderDAO.findByClientId(clientId))
                .thenReturn(orders);

        List<Order> result =
                orderService.findByClientId(clientId);


        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(orders, result);

        verify(orderDAO).findByClientId(clientId);
    }

    @Test
    void shouldFindOrdersByIntermediaryId() {


        Long intermediaryId = 20L;

        Order order1 = Order.builder()
                .id(1L)
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .build();

        List<Order> orders = List.of(order1, order2);

        when(orderDAO.findByIntermediaryId(intermediaryId))
                .thenReturn(orders);


        List<Order> result =
                orderService.findByIntermediaryId(intermediaryId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(orders, result);

        verify(orderDAO).findByIntermediaryId(intermediaryId);
    }

    @Test
    void shouldFindOrdersByTransportId() {


        Long transportId = 30L;

        Order order1 = Order.builder()
                .id(1L)
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .build();

        List<Order> orders = List.of(order1, order2);

        when(orderDAO.findByTransportId(transportId))
                .thenReturn(orders);


        List<Order> result =
                orderService.findByTransportId(transportId);


        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(orders, result);

        verify(orderDAO).findByTransportId(transportId);
    }

    @Test
    void shouldFindOrdersWithoutTransport() {


        Order order1 = Order.builder()
                .id(1L)
                .transport(null)
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .transport(null)
                .build();

        List<Order> orders = List.of(order1, order2);

        when(orderDAO.findWithoutTransport())
                .thenReturn(orders);


        List<Order> result =
                orderService.findWithoutTransport();


        assertNotNull(result);
        assertEquals(2, result.size());

        assertNull(result.get(0).getTransport());
        assertNull(result.get(1).getTransport());

        verify(orderDAO).findWithoutTransport();
    }

    @Test
    void shouldFindOrdersByExecutionDate() {

        LocalDate executionDate =
                LocalDate.of(2026, 10, 10);

        Order order1 = Order.builder()
                .id(1L)
                .executionDate(executionDate)
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .executionDate(executionDate)
                .build();

        List<Order> orders = List.of(order1, order2);

        when(orderDAO.findByExecutionDate(executionDate))
                .thenReturn(orders);


        List<Order> result =
                orderService.findByExecutionDate(executionDate);

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(executionDate, result.get(0).getExecutionDate());
        assertEquals(executionDate, result.get(1).getExecutionDate());

        verify(orderDAO).findByExecutionDate(executionDate);
    }

    @Test
    void shouldFindOrdersByExecutionDateBetween() {


        LocalDate from =
                LocalDate.of(2026, 10, 1);

        LocalDate to =
                LocalDate.of(2026, 10, 31);

        Order order1 = Order.builder()
                .id(1L)
                .executionDate(LocalDate.of(2026, 10, 5))
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .executionDate(LocalDate.of(2026, 10, 20))
                .build();

        List<Order> orders = List.of(order1, order2);

        when(orderDAO.findByExecutionDateBetween(from, to))
                .thenReturn(orders);

        List<Order> result =
                orderService.findByExecutionDateBetween(from, to);


        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(
                LocalDate.of(2026, 10, 5),
                result.get(0).getExecutionDate()
        );

        assertEquals(
                LocalDate.of(2026, 10, 20),
                result.get(1).getExecutionDate()
        );

        verify(orderDAO)
                .findByExecutionDateBetween(from, to);
    }

}

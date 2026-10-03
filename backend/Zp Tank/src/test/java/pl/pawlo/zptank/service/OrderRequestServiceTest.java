package pl.pawlo.zptank.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.OrderRequestStatus;
import pl.pawlo.zptank.domain.order.OrderRequest;
import pl.pawlo.zptank.service.dao.OrderRequestDAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderRequestServiceTest {


    @Mock
    private OrderRequestDAO orderRequestDAO;

    @InjectMocks
    private OrderRequestService orderRequestService;

    @Test
    void shouldSaveOrderRequest(){

        OrderRequest orderRequest = OrderRequest.builder()
                .id(1L)
                .price(new BigDecimal(10))
                .build();

        Mockito.when(orderRequestDAO.save(orderRequest)).thenReturn(orderRequest);

        OrderRequest save = orderRequestService.save(orderRequest);

        Assertions.assertThat(save).isEqualTo(orderRequest);
    }

    @Test
    void shouldFindOrderRequestById(){
        OrderRequest orderRequest = OrderRequest.builder()
                .id(1L)
                .price(new BigDecimal(10))
                .build();

        Mockito.when(orderRequestDAO.findById(1L)).thenReturn(Optional.of(orderRequest));

        OrderRequest find = orderRequestService.findById(1L);

        Assertions.assertThat(find).isEqualTo(orderRequest);

    }

    @Test
    void shouldFindAllOrderRequest(){
        OrderRequest orderRequest1 = OrderRequest.builder()
                .id(1L)
                .price(new BigDecimal(10))
                .build();

        OrderRequest orderRequest2 = OrderRequest.builder()
                .id(2L)
                .price(new BigDecimal(20))
                .build();

        Mockito.when(orderRequestDAO.findAll()).thenReturn(List.of(orderRequest1,orderRequest2));

        List<OrderRequest> findAll = orderRequestService.findAll();

        Assertions.assertThat(findAll).isEqualTo(List.of(orderRequest1,orderRequest2));
        Assertions.assertThat(findAll).hasSize(2);
    }

    @Test
    void shouldDeleteOrderRequest(){
        OrderRequest orderRequest1 = OrderRequest.builder()
                .id(1L)
                .price(new BigDecimal(10))
                .build();

        Mockito.when(orderRequestDAO.findById(1L)).thenReturn(Optional.of(orderRequest1));

        orderRequestService.delete(1L);

        Mockito.verify(orderRequestDAO, Mockito.times(1)).delete(orderRequest1.getId());
    }

    @Test
    void shouldUpdateOrderRequest(){
        OrderRequest orderRequest1 = OrderRequest.builder()
                .id(1L)
                .price(new BigDecimal(10))
                .build();

        OrderRequest orderRequest2 = OrderRequest.builder()
                .id(1L)
                .price(new BigDecimal(20))
                .build();

        Mockito.when(orderRequestDAO.update(orderRequest2)).thenReturn(orderRequest2);
        Mockito.when(orderRequestDAO.findById(1L)).thenReturn(Optional.of(orderRequest1));

        OrderRequest update = orderRequestService.update(1L, orderRequest2);

        Assertions.assertThat(update).isEqualTo(orderRequest2);

    }

    @Test
    void shouldUpdateOrderRequestStatus() {

        OrderRequest existingOrderRequest = OrderRequest.builder()
                .id(1L)
                .status(OrderRequestStatus.NEW)
                .price(new BigDecimal(10))
                .build();

        OrderRequest updatedOrderRequest = OrderRequest.builder()
                .id(1L)
                .status(OrderRequestStatus.CONFIRMED)
                .price(new BigDecimal(10))
                .build();

        Mockito.when(orderRequestDAO.findById(1L)).thenReturn(Optional.of(existingOrderRequest));

        Mockito.when(orderRequestDAO.update(updatedOrderRequest)).thenReturn(updatedOrderRequest);

        OrderRequest result = orderRequestService.updateStatus(1L, OrderRequestStatus.CONFIRMED);

        Assertions.assertThat(result).isEqualTo(updatedOrderRequest);

        Mockito.verify(orderRequestDAO).update(updatedOrderRequest);
    }

    @Test
    void shouldFindOrderRequestsByStatus() {

        OrderRequest orderRequest1 = OrderRequest.builder()
                .id(1L)
                .status(OrderRequestStatus.NEW)
                .price(new BigDecimal(10))
                .build();

        OrderRequest orderRequest2 = OrderRequest.builder()
                .id(2L)
                .status(OrderRequestStatus.NEW)
                .price(new BigDecimal(20))
                .build();

        Mockito.when(orderRequestDAO.findByStatus(OrderRequestStatus.NEW)).thenReturn(List.of(orderRequest1, orderRequest2));

        List<OrderRequest> result = orderRequestService.findByStatus(OrderRequestStatus.NEW);

        Assertions.assertThat(result).containsExactly(orderRequest1, orderRequest2);

        Assertions.assertThat(result).hasSize(2);

        Mockito.verify(orderRequestDAO).findByStatus(OrderRequestStatus.NEW);
    }

}

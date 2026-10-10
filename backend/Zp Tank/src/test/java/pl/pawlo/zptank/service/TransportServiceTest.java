package pl.pawlo.zptank.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.TransportStatus;
import pl.pawlo.zptank.domain.order.Order;
import pl.pawlo.zptank.domain.transport.CreateTransportRequest;
import pl.pawlo.zptank.domain.transport.Driver;
import pl.pawlo.zptank.domain.transport.LoadingTerminal;
import pl.pawlo.zptank.domain.transport.Transport;
import pl.pawlo.zptank.service.dao.TransportDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransportServiceTest {

    @Mock
    private TransportDAO transportDAO;

    @Mock
    private DriverService driverService;

    @Mock
    private LoadingTerminalService loadingTerminalService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private TransportService transportService;


    @Test
    void shouldCreateTransport() {
        Driver driver = Driver.builder().id(1L).build();
        LoadingTerminal loadingTerminal = LoadingTerminal.builder().id(2L).build();
        Order order1 = Order.builder().id(3L).build();
        Order order2 = Order.builder().id(4L).build();

        CreateTransportRequest request = CreateTransportRequest.builder()
                .driverId(1L)
                .loadingTerminalId(2L)
                .orderIds(List.of(3L, 4L))
                .loadingDate(LocalDate.now())
                .unloadingDate(LocalDate.now().plusDays(1))
                .build();

        Transport savedTransport = Transport.builder()
                .id(5L)
                .status(TransportStatus.PLANNED)
                .driver(driver)
                .loadingTerminal(loadingTerminal)
                .orders(new ArrayList<>())
                .loadingDate(request.getLoadingDate())
                .unloadingDate(request.getUnloadingDate())
                .build();

        Mockito.when(driverService.findById(1L)).thenReturn(driver);
        Mockito.when(loadingTerminalService.findById(2L)).thenReturn(loadingTerminal);
        Mockito.when(orderService.findById(3L)).thenReturn(order1);
        Mockito.when(orderService.findById(4L)).thenReturn(order2);
        Mockito.when(transportDAO.save(Mockito.any(Transport.class))).thenReturn(savedTransport);
        Mockito.when(orderService.assignTransport(order1, savedTransport)).thenReturn(order1);
        Mockito.when(orderService.assignTransport(order2, savedTransport)).thenReturn(order2);

        Transport result = transportService.createTransport(request);

        Assertions.assertThat(result.getId()).isEqualTo(5L);
        Assertions.assertThat(result.getStatus()).isEqualTo(TransportStatus.PLANNED);
        Assertions.assertThat(result.getDriver()).isEqualTo(driver);
        Assertions.assertThat(result.getLoadingTerminal()).isEqualTo(loadingTerminal);
        Assertions.assertThat(result.getOrders()).containsExactlyInAnyOrder(order1, order2);
        Assertions.assertThat(result.getLoadingDate()).isEqualTo(request.getLoadingDate());
        Assertions.assertThat(result.getUnloadingDate()).isEqualTo(request.getUnloadingDate());
    }

    @Test
    void shouldThrowWhenOrderAlreadyAssigned() {
        Order assignedOrder = Order.builder()
                .id(3L)
                .transport(Transport.builder().id(99L).build())
                .build();

        CreateTransportRequest request = CreateTransportRequest.builder()
                .driverId(1L)
                .loadingTerminalId(2L)
                .orderIds(List.of(3L))
                .loadingDate(LocalDate.now())
                .unloadingDate(LocalDate.now().plusDays(1))
                .build();

        Mockito.when(driverService.findById(1L)).thenReturn(Driver.builder().id(1L).build());
        Mockito.when(loadingTerminalService.findById(2L)).thenReturn(LoadingTerminal.builder().id(2L).build());
        Mockito.when(orderService.findById(3L)).thenReturn(assignedOrder);

        Assertions.assertThatThrownBy(() -> transportService.createTransport(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("3");

        Mockito.verify(transportDAO, Mockito.never()).save(Mockito.any());
    }
}

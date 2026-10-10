package pl.pawlo.zptank.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.TransportStatus;
import pl.pawlo.zptank.domain.order.Order;
import pl.pawlo.zptank.domain.transport.*;
import pl.pawlo.zptank.service.dao.TransportDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Test
    void shouldThrowWhenOrderListIsEmpty() {
        CreateTransportRequest request = CreateTransportRequest.builder()
                .driverId(1L)
                .loadingTerminalId(2L)
                .orderIds(List.of())
                .loadingDate(LocalDate.now())
                .unloadingDate(LocalDate.now().plusDays(1))
                .build();

        Assertions.assertThatThrownBy(() -> transportService.createTransport(request))
                .isInstanceOf(IllegalArgumentException.class);

        Mockito.verifyNoInteractions(driverService, loadingTerminalService, orderService, transportDAO);
    }

    @Test
    void shouldThrowWhenOrderIdsContainDuplicates() {
        CreateTransportRequest request = CreateTransportRequest.builder()
                .driverId(1L)
                .loadingTerminalId(2L)
                .orderIds(List.of(3L, 3L))
                .loadingDate(LocalDate.now())
                .unloadingDate(LocalDate.now().plusDays(1))
                .build();

        Assertions.assertThatThrownBy(() -> transportService.createTransport(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("duplicates");

        Mockito.verifyNoInteractions(transportDAO);
    }

    @Test
    void shouldThrowWhenUnloadingDateIsBeforeLoadingDate() {
        CreateTransportRequest request = CreateTransportRequest.builder()
                .driverId(1L)
                .loadingTerminalId(2L)
                .orderIds(List.of(3L))
                .loadingDate(LocalDate.now().plusDays(2))
                .unloadingDate(LocalDate.now())
                .build();

        Assertions.assertThatThrownBy(() -> transportService.createTransport(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unloading date");

        Mockito.verifyNoInteractions(transportDAO);
    }



    @Test
    void shouldThrowWhenDriverNotFound() {
        CreateTransportRequest request = CreateTransportRequest.builder()
                .driverId(1L)
                .loadingTerminalId(2L)
                .orderIds(List.of(3L))
                .loadingDate(LocalDate.now())
                .unloadingDate(LocalDate.now().plusDays(1))
                .build();

        Mockito.when(driverService.findById(1L)).thenThrow(new RuntimeException("Driver not found"));

        Assertions.assertThatThrownBy(() -> transportService.createTransport(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Driver");

        Mockito.verify(transportDAO, Mockito.never()).save(Mockito.any());
    }


    @Test
    void shouldDeleteTransportAndUnassignAllOrders() {
        Order order1 = Order.builder().id(3L).build();
        Order order2 = Order.builder().id(4L).build();

        Transport transport = Transport.builder()
                .id(5L)
                .status(TransportStatus.PLANNED)
                .orders(List.of(order1, order2))
                .build();

        Mockito.when(transportDAO.findById(5L)).thenReturn(Optional.of(transport));

        transportService.deleteTransport(5L);

        InOrder inOrder = Mockito.inOrder(orderService, transportDAO);
        inOrder.verify(orderService).unassignTransport(order1);
        inOrder.verify(orderService).unassignTransport(order2);
        inOrder.verify(transportDAO).delete(5L);
    }

    @Test
    void shouldDeleteTransportWithoutOrders() {
        Transport transport = Transport.builder()
                .id(5L)
                .status(TransportStatus.PLANNED)
                .orders(List.of())
                .build();

        Mockito.when(transportDAO.findById(5L)).thenReturn(Optional.of(transport));

        transportService.deleteTransport(5L);

        Mockito.verify(transportDAO).delete(5L);
        Mockito.verifyNoInteractions(orderService);
    }

    @ParameterizedTest
    @EnumSource(value = TransportStatus.class, names = "PLANNED", mode = EnumSource.Mode.EXCLUDE)
    void shouldThrowWhenDeletingTransportThatIsNotPlanned(TransportStatus status) {
        Transport transport = Transport.builder()
                .id(5L)
                .status(status)
                .orders(List.of(Order.builder().id(3L).build()))
                .build();

        Mockito.when(transportDAO.findById(5L)).thenReturn(Optional.of(transport));

        Assertions.assertThatThrownBy(() -> transportService.deleteTransport(5L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("PLANNED");

        Mockito.verifyNoInteractions(orderService);
        Mockito.verify(transportDAO, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void shouldThrowWhenDeletingNonExistingTransport() {
        Mockito.when(transportDAO.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> transportService.deleteTransport(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not found");

        Mockito.verifyNoInteractions(orderService);
        Mockito.verify(transportDAO, Mockito.never()).delete(Mockito.any());
    }



    @Test
    void shouldUpdateTransportStatus() {
        Transport transport = Transport.builder()
                .id(5L)
                .status(TransportStatus.PLANNED)
                .orders(List.of())
                .build();

        Mockito.when(transportDAO.findById(5L)).thenReturn(Optional.of(transport));
        Mockito.when(transportDAO.save(Mockito.any(Transport.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Transport result = transportService.updateStatus(5L, TransportStatus.IN_TRANSIT);

        Assertions.assertThat(result.getId()).isEqualTo(5L);
        Assertions.assertThat(result.getStatus()).isEqualTo(TransportStatus.IN_TRANSIT);
    }

    @Test
    void shouldSaveTransportWithNewStatus() {
        Transport transport = Transport.builder()
                .id(5L)
                .status(TransportStatus.PLANNED)
                .orders(List.of())
                .build();

        Mockito.when(transportDAO.findById(5L)).thenReturn(Optional.of(transport));
        Mockito.when(transportDAO.save(Mockito.any(Transport.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        transportService.updateStatus(5L, TransportStatus.IN_TRANSIT);

        ArgumentCaptor<Transport> captor = ArgumentCaptor.forClass(Transport.class);
        Mockito.verify(transportDAO).save(captor.capture());
        Assertions.assertThat(captor.getValue().getStatus()).isEqualTo(TransportStatus.IN_TRANSIT);
    }

    @Test
    void shouldKeepOtherFieldsWhenUpdatingStatus() {
        Driver driver = Driver.builder().id(1L).build();
        LoadingTerminal terminal = LoadingTerminal.builder().id(2L).build();
        Order order = Order.builder().id(3L).build();

        Transport transport = Transport.builder()
                .id(5L)
                .status(TransportStatus.PLANNED)
                .driver(driver)
                .loadingTerminal(terminal)
                .orders(List.of(order))
                .loadingDate(LocalDate.of(2026, 10, 12))
                .unloadingDate(LocalDate.of(2026, 10, 13))
                .build();

        Mockito.when(transportDAO.findById(5L)).thenReturn(Optional.of(transport));
        Mockito.when(transportDAO.save(Mockito.any(Transport.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Transport result = transportService.updateStatus(5L, TransportStatus.IN_TRANSIT);

        Assertions.assertThat(result.getDriver()).isEqualTo(driver);
        Assertions.assertThat(result.getLoadingTerminal()).isEqualTo(terminal);
        Assertions.assertThat(result.getOrders()).containsExactly(order);
        Assertions.assertThat(result.getLoadingDate()).isEqualTo(LocalDate.of(2026, 10, 12));
        Assertions.assertThat(result.getUnloadingDate()).isEqualTo(LocalDate.of(2026, 10, 13));
    }

    @Test
    void shouldNotTouchOrdersWhenUpdatingStatus() {
        Transport transport = Transport.builder()
                .id(5L)
                .status(TransportStatus.PLANNED)
                .orders(List.of(Order.builder().id(3L).build()))
                .build();

        Mockito.when(transportDAO.findById(5L)).thenReturn(Optional.of(transport));
        Mockito.when(transportDAO.save(Mockito.any(Transport.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        transportService.updateStatus(5L, TransportStatus.IN_TRANSIT);

        Mockito.verifyNoInteractions(orderService);
    }

    @Test
    void shouldThrowWhenUpdatingStatusOfNonExistingTransport() {
        Mockito.when(transportDAO.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> transportService.updateStatus(99L, TransportStatus.IN_TRANSIT))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not found");

        Mockito.verify(transportDAO, Mockito.never()).save(Mockito.any());
    }


    @Test
    void shouldUpdateOnlyDriverAndLeaveRestUntouched() {
        Order order = Order.builder().id(3L).build();
        Driver oldDriver = Driver.builder().id(1L).build();
        Driver newDriver = Driver.builder().id(7L).build();
        LoadingTerminal terminal = LoadingTerminal.builder().id(2L).build();

        Transport existing = Transport.builder()
                .id(5L)
                .status(TransportStatus.PLANNED)
                .driver(oldDriver)
                .loadingTerminal(terminal)
                .orders(List.of(order))
                .loadingDate(LocalDate.of(2026, 10, 12))
                .unloadingDate(LocalDate.of(2026, 10, 13))
                .build();

        Mockito.when(transportDAO.findById(5L)).thenReturn(Optional.of(existing));
        Mockito.when(driverService.findById(7L)).thenReturn(newDriver);
        Mockito.when(transportDAO.save(Mockito.any(Transport.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Transport result = transportService.updateTransport(5L,
                UpdateTransportRequest.builder().driverId(7L).build());

        Assertions.assertThat(result.getDriver()).isEqualTo(newDriver);
        Assertions.assertThat(result.getLoadingTerminal()).isEqualTo(terminal);
        Assertions.assertThat(result.getLoadingDate()).isEqualTo(LocalDate.of(2026, 10, 12));
        Assertions.assertThat(result.getUnloadingDate()).isEqualTo(LocalDate.of(2026, 10, 13));
        Assertions.assertThat(result.getOrders()).containsExactly(order);

        Mockito.verifyNoInteractions(orderService, loadingTerminalService);
    }

    @Test
    void shouldUpdateOnlyTerminal() {
        Driver driver = Driver.builder().id(1L).build();
        LoadingTerminal oldTerminal = LoadingTerminal.builder().id(2L).build();
        LoadingTerminal newTerminal = LoadingTerminal.builder().id(8L).build();

        Transport existing = Transport.builder()
                .id(5L)
                .status(TransportStatus.PLANNED)
                .driver(driver)
                .loadingTerminal(oldTerminal)
                .orders(List.of())
                .loadingDate(LocalDate.of(2026, 10, 12))
                .unloadingDate(LocalDate.of(2026, 10, 13))
                .build();

        Mockito.when(transportDAO.findById(5L)).thenReturn(Optional.of(existing));
        Mockito.when(loadingTerminalService.findById(8L)).thenReturn(newTerminal);
        Mockito.when(transportDAO.save(Mockito.any(Transport.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Transport result = transportService.updateTransport(5L,
                UpdateTransportRequest.builder().loadingTerminalId(8L).build());

        Assertions.assertThat(result.getLoadingTerminal()).isEqualTo(newTerminal);
        Assertions.assertThat(result.getDriver()).isEqualTo(driver);

        Mockito.verifyNoInteractions(driverService, orderService);
    }

    @Test
    void shouldUpdateOnlyDates() {
        Driver driver = Driver.builder().id(1L).build();

        Transport existing = Transport.builder()
                .id(5L)
                .status(TransportStatus.PLANNED)
                .driver(driver)
                .loadingTerminal(LoadingTerminal.builder().id(2L).build())
                .orders(List.of())
                .loadingDate(LocalDate.of(2026, 10, 12))
                .unloadingDate(LocalDate.of(2026, 10, 13))
                .build();

        Mockito.when(transportDAO.findById(5L)).thenReturn(Optional.of(existing));
        Mockito.when(transportDAO.save(Mockito.any(Transport.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Transport result = transportService.updateTransport(5L,
                UpdateTransportRequest.builder()
                        .loadingDate(LocalDate.of(2026, 10, 14))
                        .unloadingDate(LocalDate.of(2026, 10, 15))
                        .build());

        Assertions.assertThat(result.getLoadingDate()).isEqualTo(LocalDate.of(2026, 10, 14));
        Assertions.assertThat(result.getUnloadingDate()).isEqualTo(LocalDate.of(2026, 10, 15));
        Assertions.assertThat(result.getDriver()).isEqualTo(driver);

        Mockito.verifyNoInteractions(driverService, loadingTerminalService, orderService);
    }

    @Test
    void shouldThrowWhenNewLoadingDateIsAfterExistingUnloadingDate() {
        Transport existing = Transport.builder()
                .id(5L)
                .status(TransportStatus.PLANNED)
                .orders(List.of())
                .loadingDate(LocalDate.of(2026, 10, 12))
                .unloadingDate(LocalDate.of(2026, 10, 13))
                .build();

        Mockito.when(transportDAO.findById(5L)).thenReturn(Optional.of(existing));

        Assertions.assertThatThrownBy(() -> transportService.updateTransport(5L,
                        UpdateTransportRequest.builder().loadingDate(LocalDate.of(2026, 10, 20)).build()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unloading date");

        Mockito.verify(transportDAO, Mockito.never()).save(Mockito.any());
    }

    @ParameterizedTest
    @EnumSource(value = TransportStatus.class, names = "PLANNED", mode = EnumSource.Mode.EXCLUDE)
    void shouldThrowWhenUpdatingTransportThatIsNotPlanned(TransportStatus status) {
        Transport existing = Transport.builder()
                .id(5L)
                .status(status)
                .orders(List.of())
                .loadingDate(LocalDate.of(2026, 10, 12))
                .unloadingDate(LocalDate.of(2026, 10, 13))
                .build();

        Mockito.when(transportDAO.findById(5L)).thenReturn(Optional.of(existing));

        Assertions.assertThatThrownBy(() -> transportService.updateTransport(5L,
                        UpdateTransportRequest.builder().driverId(7L).build()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("PLANNED");

        Mockito.verify(transportDAO, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingTransport() {
        Mockito.when(transportDAO.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> transportService.updateTransport(99L,
                        UpdateTransportRequest.builder().driverId(7L).build()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not found");

        Mockito.verify(transportDAO, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldThrowWhenOrderIdsIsEmptyList() {
        Transport existing = Transport.builder()
                .id(5L)
                .status(TransportStatus.PLANNED)
                .orders(List.of())
                .loadingDate(LocalDate.of(2026, 10, 12))
                .unloadingDate(LocalDate.of(2026, 10, 13))
                .build();

        Mockito.when(transportDAO.findById(5L)).thenReturn(Optional.of(existing));

        Assertions.assertThatThrownBy(() -> transportService.updateTransport(5L,
                        UpdateTransportRequest.builder().orderIds(List.of()).build()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("empty");

        Mockito.verify(transportDAO, Mockito.never()).save(Mockito.any());
        Mockito.verifyNoInteractions(orderService);
    }

    @Test
    void shouldThrowWhenOrderIsAssignedToAnotherTransport() {
        Transport existing = Transport.builder()
                .id(5L)
                .status(TransportStatus.PLANNED)
                .orders(List.of())
                .loadingDate(LocalDate.of(2026, 10, 12))
                .unloadingDate(LocalDate.of(2026, 10, 13))
                .build();

        Order takenOrder = Order.builder()
                .id(3L)
                .transport(Transport.builder().id(99L).build())
                .build();

        Mockito.when(transportDAO.findById(5L)).thenReturn(Optional.of(existing));
        Mockito.when(orderService.findById(3L)).thenReturn(takenOrder);

        Assertions.assertThatThrownBy(() -> transportService.updateTransport(5L,
                        UpdateTransportRequest.builder().orderIds(List.of(3L)).build()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("[3]");

        Mockito.verify(transportDAO, Mockito.never()).save(Mockito.any());
        Mockito.verify(orderService, Mockito.never()).assignTransport(Mockito.any(), Mockito.any());
        Mockito.verify(orderService, Mockito.never()).unassignTransport(Mockito.any());
    }

    @Test
    void shouldNotTreatOrderAlreadyInThisTransportAsConflict() {
        Transport sameTransport = Transport.builder().id(5L).build();
        Order orderInThisTransport = Order.builder().id(3L).transport(sameTransport).build();

        Transport existing = Transport.builder()
                .id(5L)
                .status(TransportStatus.PLANNED)
                .orders(List.of(orderInThisTransport))
                .loadingDate(LocalDate.of(2026, 10, 12))
                .unloadingDate(LocalDate.of(2026, 10, 13))
                .build();

        Mockito.when(transportDAO.findById(5L)).thenReturn(Optional.of(existing));
        Mockito.when(orderService.findById(3L)).thenReturn(orderInThisTransport);
        Mockito.when(transportDAO.save(Mockito.any(Transport.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Transport result = transportService.updateTransport(5L,
                UpdateTransportRequest.builder().orderIds(List.of(3L)).build());

        Assertions.assertThat(result.getOrders()).containsExactly(orderInThisTransport);

        Mockito.verify(orderService, Mockito.never()).assignTransport(Mockito.any(), Mockito.any());
        Mockito.verify(orderService, Mockito.never()).unassignTransport(Mockito.any());
    }

    @Test
    void shouldUnassignRemovedOrderAndAssignNewOne() {
        Transport sameTransport = Transport.builder().id(5L).build();
        Order removed = Order.builder().id(3L).transport(sameTransport).build();
        Order kept = Order.builder().id(4L).transport(sameTransport).build();
        Order added = Order.builder().id(6L).build();

        Transport existing = Transport.builder()
                .id(5L)
                .status(TransportStatus.PLANNED)
                .orders(List.of(removed, kept))
                .loadingDate(LocalDate.of(2026, 10, 12))
                .unloadingDate(LocalDate.of(2026, 10, 13))
                .build();

        Mockito.when(transportDAO.findById(5L)).thenReturn(Optional.of(existing));
        Mockito.when(orderService.findById(4L)).thenReturn(kept);
        Mockito.when(orderService.findById(6L)).thenReturn(added);
        Mockito.when(transportDAO.save(Mockito.any(Transport.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        Mockito.when(orderService.assignTransport(Mockito.eq(added), Mockito.any())).thenReturn(added);

        Transport result = transportService.updateTransport(5L,
                UpdateTransportRequest.builder().orderIds(List.of(4L, 6L)).build());

        Assertions.assertThat(result.getOrders()).containsExactlyInAnyOrder(kept, added);

        Mockito.verify(orderService).unassignTransport(removed);
        Mockito.verify(orderService, Mockito.never()).unassignTransport(kept);
        Mockito.verify(orderService).assignTransport(Mockito.eq(added), Mockito.any());
        Mockito.verify(orderService, Mockito.never()).assignTransport(Mockito.eq(kept), Mockito.any());
    }

    @Test
    void shouldFindTransportsByDriverId() {
        Transport transport = Transport.builder()
                .id(5L)
                .driver(Driver.builder().id(7L).build())
                .build();


        Mockito.when(transportDAO.findByDriverId(7L)).thenReturn(List.of(transport));
        Mockito.when(driverService.findById(7L)).thenReturn(Driver.builder().id(7L).build());

        List<Transport> result = transportService.findByDriverId(7L);

        Assertions.assertThat(result).containsExactly(transport);
    }

    @Test
    void shouldFindTransportsByStatus() {
        Transport transport1 = Transport.builder()
                .id(5L)
                .status(TransportStatus.PLANNED)
                .build();

        Transport transport2 = Transport.builder()
                .id(6L)
                .status(TransportStatus.PLANNED)
                .build();

        Mockito.when(transportDAO.findByStatus(TransportStatus.PLANNED)).thenReturn(List.of(transport1, transport2));

        List<Transport> result = transportService.findByStatus(TransportStatus.PLANNED);

        Assertions.assertThat(result).containsExactlyInAnyOrder(transport1, transport2);
    }


    @Test
    void shouldFindAllTransports() {
        Transport transport1 = Transport.builder()
                .id(5L)
                .build();

        Transport transport2 = Transport.builder()
                .id(6L)
                .build();

        Mockito.when(transportDAO.findAll()).thenReturn(List.of(transport1, transport2));

        List<Transport> result = transportService.findAll();

        Assertions.assertThat(result).containsExactlyInAnyOrder(transport1, transport2);
    }


}

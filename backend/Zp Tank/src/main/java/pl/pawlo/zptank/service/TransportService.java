package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawlo.zptank.domain.TransportStatus;
import pl.pawlo.zptank.domain.order.Order;
import pl.pawlo.zptank.domain.transport.CreateTransportRequest;
import pl.pawlo.zptank.domain.transport.Driver;
import pl.pawlo.zptank.domain.transport.LoadingTerminal;
import pl.pawlo.zptank.domain.transport.Transport;
import pl.pawlo.zptank.service.dao.TransportDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class TransportService {

    private final TransportDAO transportDAO;
    private final DriverService driverService;
    private final LoadingTerminalService loadingTerminalService;
    private final OrderService orderService;


    @Transactional
    public Transport createTransport(CreateTransportRequest createTransportRequest) {
        validate(createTransportRequest.getOrderIds(), createTransportRequest.getLoadingDate(), createTransportRequest.getUnloadingDate());

        Driver driver = driverService.findById(createTransportRequest.getDriverId());
        LoadingTerminal loadingTerminal = loadingTerminalService.findById(createTransportRequest.getLoadingTerminalId());
        List<Order> orders = createTransportRequest.getOrderIds().stream()
                .map(orderService::findById)
                .toList();

        List<Long> alreadyAssigned = orders.stream()
                .filter(order -> order.getTransport() != null)
                .map(Order::getId)
                .toList();

        Transport transport = Transport.builder()
                .status(TransportStatus.PLANNED)
                .driver(driver)
                .loadingTerminal(loadingTerminal)
                .orders(new ArrayList<>())
                .loadingDate(createTransportRequest.getLoadingDate())
                .unloadingDate(createTransportRequest.getUnloadingDate())
                .build();

        if (!alreadyAssigned.isEmpty()) {
            throw new IllegalStateException(
                    "Orders already assigned to a transport: " + alreadyAssigned
            );
        }

        Transport savedTransport = transportDAO.save(transport);
        List<Order> assignedOrders = orders.stream()
                .map(order -> orderService.assignTransport(order, savedTransport))
                .toList();

        return Transport.builder()
                .id(savedTransport.getId())
                .status(savedTransport.getStatus())
                .driver(savedTransport.getDriver())
                .loadingTerminal(savedTransport.getLoadingTerminal())
                .orders(assignedOrders)
                .loadingDate(savedTransport.getLoadingDate())
                .unloadingDate(savedTransport.getUnloadingDate())
                .build();
    }

    private void validate(
            List<Long> ordersId,
            LocalDate dateLoading,
            LocalDate dateUnloading
    ) {
        if (ordersId == null || ordersId.isEmpty()) {
            throw new IllegalArgumentException("Order list cannot be empty");
        }
        if (ordersId.stream().anyMatch(java.util.Objects::isNull)) {
            throw new IllegalArgumentException("Order IDs cannot be null");
        }
        if (new HashSet<>(ordersId).size() != ordersId.size()) {
            throw new IllegalArgumentException("Order list contains duplicates");
        }
        if (dateLoading == null || dateUnloading == null) {
            throw new IllegalArgumentException("Loading and unloading dates are required");
        }
        if (dateUnloading.isBefore(dateLoading)) {
            throw new IllegalArgumentException(
                    "Unloading date cannot be before loading date"
            );
        }
    }

}

package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawlo.zptank.domain.TransportStatus;
import pl.pawlo.zptank.domain.OrderStatus;
import pl.pawlo.zptank.domain.order.Order;
import pl.pawlo.zptank.domain.transport.*;
import pl.pawlo.zptank.service.dao.TransportDAO;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class TransportService {

    private final TransportDAO transportDAO;
    private final DriverService driverService;
    private final LoadingTerminalService loadingTerminalService;
    private final OrderService orderService;


    @Transactional
    public Transport createTransport(CreateTransportRequest createTransportRequest) {
        validateOrderIds(createTransportRequest.getOrderIds());
        validateDates(createTransportRequest.getLoadingDate(), createTransportRequest.getUnloadingDate());

        Driver driver = driverService.findById(createTransportRequest.getDriverId());
        LoadingTerminal loadingTerminal = loadingTerminalService.findById(createTransportRequest.getLoadingTerminalId());
        validateDriverAndVehicle(driver, createTransportRequest.getUnloadingDate());
        validateDriverAvailability(
                driver.getId(),
                createTransportRequest.getLoadingDate(),
                createTransportRequest.getUnloadingDate(),
                null
        );
        List<Order> orders = createTransportRequest.getOrderIds().stream()
                .map(orderService::findById)
                .toList();

        validateOrdersCanBeTransported(orders);

        List<Long> alreadyAssigned = orders.stream()
                .filter(order -> order.getTransport() != null)
                .map(Order::getId)
                .toList();

        if (!alreadyAssigned.isEmpty()) {
            throw new IllegalStateException(
                    "Orders already assigned to a transport: " + alreadyAssigned
            );
        }

        Transport transport = Transport.builder()
                .status(TransportStatus.PLANNED)
                .driver(driver)
                .loadingTerminal(loadingTerminal)
                .orders(new ArrayList<>())
                .loadingDate(createTransportRequest.getLoadingDate())
                .unloadingDate(createTransportRequest.getUnloadingDate())
                .build();

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

    public Transport findById(Long id) {
        return transportDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Transport not found"));
    }

    @Transactional
    public Transport updateTransport(Long transportId, UpdateTransportRequest request) {
        Transport transport = findById(transportId);

        if (transport.getStatus() != TransportStatus.PLANNED) {
            throw new IllegalStateException(
                    "Only PLANNED transport can be edited, current status: " + transport.getStatus()
            );
        }

        Driver driver = request.getDriverId() != null
                ? driverService.findById(request.getDriverId())
                : transport.getDriver();

        LoadingTerminal loadingTerminal = request.getLoadingTerminalId() != null
                ? loadingTerminalService.findById(request.getLoadingTerminalId())
                : transport.getLoadingTerminal();

        LocalDate loadingDate = request.getLoadingDate() != null
                ? request.getLoadingDate()
                : transport.getLoadingDate();

        LocalDate unloadingDate = request.getUnloadingDate() != null
                ? request.getUnloadingDate()
                : transport.getUnloadingDate();

        validateDates(loadingDate, unloadingDate);

        boolean ordersChanged = request.getOrderIds() != null;
        List<Order> requestedOrders = List.of();

        if (ordersChanged) {
            validateOrderIds(request.getOrderIds());

            requestedOrders = request.getOrderIds().stream()
                    .map(orderService::findById)
                    .toList();

            List<Long> assignedElsewhere = requestedOrders.stream()
                    .filter(order -> order.getTransport() != null
                            && !order.getTransport().getId().equals(transportId))
                    .map(Order::getId)
                    .toList();

            if (!assignedElsewhere.isEmpty()) {
                throw new IllegalStateException(
                        "Orders already assigned to another transport: " + assignedElsewhere
                );
            }
        }

        if (driver != null) {
            validateDriverAvailability(driver.getId(), loadingDate, unloadingDate, transportId);
        }

        Transport savedTransport = transportDAO.save(
                Transport.builder()
                        .id(transport.getId())
                        .status(transport.getStatus())
                        .driver(driver)
                        .loadingTerminal(loadingTerminal)
                        .orders(transport.getOrders())
                        .loadingDate(loadingDate)
                        .unloadingDate(unloadingDate)
                        .build()
        );

        List<Order> finalOrders = transport.getOrders();

        if (ordersChanged) {
            Set<Long> requestedIds = new HashSet<>(request.getOrderIds());

            transport.getOrders().stream()
                    .filter(order -> !requestedIds.contains(order.getId()))
                    .forEach(orderService::unassignTransport);

            finalOrders = requestedOrders.stream()
                    .map(order -> order.getTransport() == null
                            ? orderService.assignTransport(order, savedTransport)
                            : order)
                    .toList();
        }

        return Transport.builder()
                .id(savedTransport.getId())
                .status(savedTransport.getStatus())
                .driver(savedTransport.getDriver())
                .loadingTerminal(savedTransport.getLoadingTerminal())
                .orders(finalOrders)
                .loadingDate(savedTransport.getLoadingDate())
                .unloadingDate(savedTransport.getUnloadingDate())
                .build();
    }

    @Transactional
    public void deleteTransport(Long transportId) {
        Transport transport = findById(transportId);

        if (transport.getStatus() != TransportStatus.PLANNED) {
            throw new IllegalStateException(
                    "Only PLANNED transport can be deleted, current status: " + transport.getStatus()
            );
        }

        transport.getOrders().forEach(orderService::unassignTransport);
        transportDAO.delete(transportId);
    }

    @Transactional
    public Transport updateStatus(Long transportId, TransportStatus newStatus) {
        Transport transport = findById(transportId);

        return transportDAO.save(
                Transport.builder()
                        .id(transport.getId())
                        .status(newStatus)
                        .driver(transport.getDriver())
                        .loadingTerminal(transport.getLoadingTerminal())
                        .orders(transport.getOrders())
                        .loadingDate(transport.getLoadingDate())
                        .unloadingDate(transport.getUnloadingDate())
                        .build()
        );
    }

    public List<Transport> findAll() {
        return transportDAO.findAll();
    }


    public List<Transport> findByStatus(TransportStatus status) {
        return transportDAO.findByStatus(status);
    }

    public List<Transport> findByDriverId(Long driverId) {
        Driver driver = driverService.findById(driverId);
        return transportDAO.findByDriverId(driver.getId());
    }



    private void validateOrderIds(List<Long> orderIds) {
        if (orderIds == null || orderIds.isEmpty()) {
            throw new IllegalArgumentException("Order list cannot be empty");
        }
        if (orderIds.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Order IDs cannot be null");
        }
        if (new HashSet<>(orderIds).size() != orderIds.size()) {
            throw new IllegalArgumentException("Order list contains duplicates");
        }
    }

    private void validateDates(LocalDate loadingDate, LocalDate unloadingDate) {
        if (loadingDate == null || unloadingDate == null) {
            throw new IllegalArgumentException("Loading and unloading dates are required");
        }
        if (unloadingDate.isBefore(loadingDate)) {
            throw new IllegalArgumentException("Unloading date cannot be before loading date");
        }
    }

    private void validateDriverAvailability(
            Long driverId,
            LocalDate loadingDate,
            LocalDate unloadingDate,
            Long excludedTransportId
    ) {
        if (transportDAO.existsDriverScheduleConflict(
                driverId, loadingDate, unloadingDate, excludedTransportId
        )) {
            throw new IllegalStateException(
                    "Driver " + driverId + " is already assigned to another transport in this period"
            );
        }
    }

    private void validateDriverAndVehicle(Driver driver, LocalDate validThrough) {
        if (!Boolean.TRUE.equals(driver.getActive())) {
            throw new IllegalStateException("Driver is inactive");
        }
        if (driver.getTransportSet() == null) {
            throw new IllegalStateException("Driver has no transport set assigned");
        }
        if (!driver.getTransportSet().isActive()) {
            throw new IllegalStateException("Transport set is inactive");
        }

        validateDate(driver.getDriverCardValidUntil(), validThrough, "Driver card");
        validateDate(driver.getAdrValidUntil(), validThrough, "Driver ADR");
        validateDate(driver.getIdentityDocumentValidUntil(), validThrough, "Driver identity document");
        validateDate(driver.getPsychologicalExaminationValidUntil(), validThrough, "Driver psychological examination");
        validateDate(driver.getTdtValidUntil(), validThrough, "Driver TDT");
        validateDate(driver.getDrivingLicenseValidUntil(), validThrough, "Driver driving license");
        validateDate(driver.getHealthAndSafetyTrainingValidUntil(), validThrough, "Driver health and safety training");
        validateDate(driver.getMedicalExaminationValidUntil(), validThrough, "Driver medical examination");

        TransportSet transportSet = driver.getTransportSet();
        if (transportSet.getTankerTruck() != null) {
            validateTankerTruck(transportSet.getTankerTruck(), validThrough);
        } else {
            if (transportSet.getTractor() == null || transportSet.getTrailer() == null) {
                throw new IllegalStateException("Transport set configuration is incomplete");
            }
            validateTractor(transportSet.getTractor(), validThrough);
            validateTrailer(transportSet.getTrailer(), validThrough);
        }
    }

    private void validateOrdersCanBeTransported(List<Order> orders) {
        List<Long> invalidOrderIds = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.CANCELLED
                        || order.getStatus() == OrderStatus.DELIVERED)
                .map(Order::getId)
                .toList();

        if (!invalidOrderIds.isEmpty()) {
            throw new IllegalStateException(
                    "Cancelled or delivered orders cannot be assigned: " + invalidOrderIds
            );
        }
    }

    private void validateTractor(Tractor tractor, LocalDate validThrough) {
        validateDate(tractor.getInsuranceValidUntil(), validThrough, "Tractor insurance");
        validateDate(tractor.getPeriodicInspectionDate(), validThrough, "Tractor periodic inspection");
    }

    private void validateTrailer(Trailer trailer, LocalDate validThrough) {
        validateDate(trailer.getInsuranceValidUntil(), validThrough, "Trailer insurance");
        validateDate(trailer.getPeriodicInspectionDate(), validThrough, "Trailer periodic inspection");
        validateDate(trailer.getIntermediateInspectionDate(), validThrough, "Trailer intermediate inspection");
        validateDate(trailer.getRedStripeValidUntil(), validThrough, "Trailer red stripe");
        validateDate(trailer.getOnLegalizationValidUntil(), validThrough, "Trailer ON legalization");
        validateDate(trailer.getPbLegalizationValidUntil(), validThrough, "Trailer PB legalization");
    }

    private void validateTankerTruck(TankerTruck tankerTruck, LocalDate validThrough) {
        validateDate(tankerTruck.getInsuranceValidUntil(), validThrough, "Tanker truck insurance");
        validateDate(tankerTruck.getPeriodicInspectionDate(), validThrough, "Tanker truck periodic inspection");
        validateDate(tankerTruck.getIntermediateInspectionDate(), validThrough, "Tanker truck intermediate inspection");
        validateDate(tankerTruck.getRedStripeValidUntil(), validThrough, "Tanker truck red stripe");
        validateDate(tankerTruck.getOnLegalizationValidUntil(), validThrough, "Tanker truck ON legalization");
        validateDate(tankerTruck.getPbLegalizationValidUntil(), validThrough, "Tanker truck PB legalization");
    }

    private void validateDate(LocalDate date, LocalDate validThrough, String documentName) {
        if (date == null || date.isBefore(validThrough)) {
            throw new IllegalStateException(
                    documentName + " is missing or expires before transport ends"
            );
        }
    }

}

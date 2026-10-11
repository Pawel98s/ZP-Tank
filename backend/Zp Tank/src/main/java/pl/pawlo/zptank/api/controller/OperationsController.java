package pl.pawlo.zptank.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.service.*;
import java.time.LocalDate;
import java.util.EnumMap;
import pl.pawlo.zptank.domain.OrderStatus;
import pl.pawlo.zptank.domain.TransportStatus;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/operations")
public class OperationsController {
    private final OrderService orders;
    private final TransportService transports;
    private final ExpiryAlertService expiryAlerts;
    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {
        var allOrders = orders.findAll();
        var allTransports = transports.findAll();
        LocalDate today = LocalDate.now();
        long delivered = allOrders.stream().filter(o -> o.getDeliveryDate() != null).count();
        long onTime = allOrders.stream().filter(o -> o.getDeliveryDate() != null && o.getPlannedDeliveryDate() != null
                && !o.getDeliveryDate().isAfter(o.getPlannedDeliveryDate())).count();
        long overdue = allOrders.stream().filter(o -> o.getDeliveryDate() == null
                && o.getPlannedDeliveryDate() != null && o.getPlannedDeliveryDate().isBefore(today)).count();
        long todayTransports = allTransports.stream().filter(t -> today.equals(t.getLoadingDate())).count();
        long tomorrowTransports = allTransports.stream().filter(t -> today.plusDays(1).equals(t.getLoadingDate())).count();
        long withoutVehicle = allTransports.stream().filter(t -> t.getDriver() == null
                || t.getDriver().getTransportSet() == null || !t.getDriver().getTransportSet().isActive()).count();
        Map<String, Long> orderStatusTotals = new java.util.LinkedHashMap<>();
        for (OrderStatus status : OrderStatus.values()) {
            orderStatusTotals.put(status.name(), allOrders.stream().filter(o -> o.getStatus() == status).count());
        }
        Map<String, Long> transportStatusTotals = new java.util.LinkedHashMap<>();
        for (TransportStatus status : TransportStatus.values()) {
            transportStatusTotals.put(status.name(), allTransports.stream().filter(t -> t.getStatus() == status).count());
        }
        Map<String, Object> response = new java.util.LinkedHashMap<>();
        response.put("orders", allOrders.size());
        response.put("transports", allTransports.size());
        response.put("newOrders", allOrders.stream().filter(o -> o.getStatus() == OrderStatus.NEW).count());
        response.put("todayTransports", todayTransports);
        response.put("tomorrowTransports", tomorrowTransports);
        response.put("overdueOrders", overdue);
        response.put("transportsWithoutDriverOrVehicle", withoutVehicle);
        response.put("expiringDocuments", expiryAlerts.find(30, null, null).size());
        response.put("orderStatusTotals", orderStatusTotals);
        response.put("transportStatusTotals", transportStatusTotals);
        response.put("deliveredOrders", delivered);
        response.put("onTimeDeliveries", onTime);
        response.put("onTimeDeliveryRate", delivered == 0 ? 0d : (double) onTime / delivered);
        return response;
    }
}

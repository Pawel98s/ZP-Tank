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
        LocalDate today = LocalDate.now();
        long orderCount = orders.count();
        long transportCount = transports.count();
        long delivered = orders.countDelivered();
        long onTime = orders.countOnTimeDeliveries();
        long overdue = orders.countOverdue(today);
        long todayTransports = transports.countByLoadingDate(today);
        long tomorrowTransports = transports.countByLoadingDate(today.plusDays(1));
        long withoutVehicle = transports.countWithoutDriverOrVehicle();
        Map<String, Long> orderStatusTotals = new java.util.LinkedHashMap<>();
        for (OrderStatus status : OrderStatus.values()) {
            orderStatusTotals.put(status.name(), orders.countByStatus(status));
        }
        Map<String, Long> transportStatusTotals = new java.util.LinkedHashMap<>();
        for (TransportStatus status : TransportStatus.values()) {
            transportStatusTotals.put(status.name(), transports.countByStatus(status));
        }
        Map<String, Object> response = new java.util.LinkedHashMap<>();
        response.put("orders", orderCount);
        response.put("transports", transportCount);
        response.put("newOrders", orders.countByStatus(OrderStatus.NEW));
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

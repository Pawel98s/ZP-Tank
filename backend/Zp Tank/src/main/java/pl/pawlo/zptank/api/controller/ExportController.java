package pl.pawlo.zptank.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.service.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/export")
public class ExportController {
    private final OrderService orders;
    private final TransportService transports;
    @GetMapping(value = "/orders.csv", produces = "text/csv")
    public ResponseEntity<byte[]> orders() {
        String csv = "id,status,executionDate,deliveryDate,waybill\n" + orders.findAll().stream()
                .map(o -> String.join(",", csv(o.getId()), csv(o.getStatus()), csv(o.getExecutionDate()),
                        csv(o.getDeliveryDate()), csv(o.getWaybill() == null ? null : o.getWaybill().getWaybillNumber())))
                .collect(Collectors.joining("\n"));
        return response(csv, "orders.csv");
    }
    @GetMapping(value = "/transports.csv", produces = "text/csv")
    public ResponseEntity<byte[]> transports() {
        String csv = "id,status,driver,loadingDate,unloadingDate\n" + transports.findAll().stream()
                .map(t -> String.join(",", csv(t.getId()), csv(t.getStatus()),
                        csv(t.getDriver() == null ? null : t.getDriver().getLastName()), csv(t.getLoadingDate()), csv(t.getUnloadingDate())))
                .collect(Collectors.joining("\n"));
        return response(csv, "transports.csv");
    }
    @GetMapping(value = "/reports.csv", produces = "text/csv")
    public ResponseEntity<byte[]> reports() {
        return response("metric,value\norders," + orders.findAll().size() + "\ntransports," + transports.findAll().size(), "reports.csv");
    }
    private ResponseEntity<byte[]> response(String body, String name) {
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"")
                .contentType(MediaType.parseMediaType("text/csv")).body(body.getBytes(StandardCharsets.UTF_8));
    }
    private String csv(Object value) {
        if (value == null) return "";
        String s = String.valueOf(value).replace("\"", "\"\"");
        return "\"" + s + "\"";
    }
}

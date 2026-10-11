package pl.pawlo.zptank.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.ExpiryAlertDTO;
import pl.pawlo.zptank.service.ExpiryAlertService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expiry-alerts")
public class ExpiryAlertController {
    private final ExpiryAlertService service;
    @GetMapping
    public List<ExpiryAlertDTO> find(@RequestParam(required = false) Integer window,
                                     @RequestParam(required = false) Boolean expired,
                                     @RequestParam(required = false) Boolean unavailable) {
        return service.find(window, expired, unavailable);
    }
}

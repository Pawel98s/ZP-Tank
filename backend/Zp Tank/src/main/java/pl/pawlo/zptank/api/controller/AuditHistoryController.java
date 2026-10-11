package pl.pawlo.zptank.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.audit.AuditHistoryDTO;
import pl.pawlo.zptank.service.AuditHistoryService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/audit")
public class AuditHistoryController {
    private final AuditHistoryService service;
    @GetMapping("/{entityType}/{entityId}")
    public List<AuditHistoryDTO> find(@PathVariable String entityType, @PathVariable Long entityId) {
        return service.find(entityType, entityId);
    }
}

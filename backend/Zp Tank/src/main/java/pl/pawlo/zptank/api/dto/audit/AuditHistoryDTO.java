package pl.pawlo.zptank.api.dto.audit;

import java.time.LocalDateTime;

public record AuditHistoryDTO(Long id, String entityType, Long entityId, String action,
                              String actor, String beforeValue, String afterValue, LocalDateTime occurredAt) {}

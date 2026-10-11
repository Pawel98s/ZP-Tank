package pl.pawlo.zptank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.zptank.api.dto.audit.AuditHistoryDTO;
import pl.pawlo.zptank.database.entity.audit.AuditHistoryEntity;
import pl.pawlo.zptank.database.repository.jpa.audit.AuditHistoryJpaRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditHistoryService {
    private final AuditHistoryJpaRepository repository;

    public void record(String type, Long id, String action, String before, String after) {
        repository.save(AuditHistoryEntity.builder().entityType(type).entityId(id).action(action)
                .actor("system").beforeValue(before).afterValue(after).occurredAt(LocalDateTime.now()).build());
    }
    public List<AuditHistoryDTO> find(String type, Long id) {
        return repository.findByEntityTypeAndEntityIdOrderByOccurredAtDesc(type, id).stream()
                .map(a -> new AuditHistoryDTO(a.getId(), a.getEntityType(), a.getEntityId(), a.getAction(),
                        a.getActor(), a.getBeforeValue(), a.getAfterValue(), a.getOccurredAt())).toList();
    }
}

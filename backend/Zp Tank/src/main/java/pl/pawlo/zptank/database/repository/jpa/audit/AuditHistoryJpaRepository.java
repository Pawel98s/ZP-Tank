package pl.pawlo.zptank.database.repository.jpa.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pawlo.zptank.database.entity.audit.AuditHistoryEntity;
import java.util.List;

public interface AuditHistoryJpaRepository extends JpaRepository<AuditHistoryEntity, Long> {
    List<AuditHistoryEntity> findByEntityTypeAndEntityIdOrderByOccurredAtDesc(String entityType, Long entityId);
}

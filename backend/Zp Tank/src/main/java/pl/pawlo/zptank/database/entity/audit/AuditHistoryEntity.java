package pl.pawlo.zptank.database.entity.audit;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_history")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class AuditHistoryEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50) private String entityType;
    @Column(nullable = false) private Long entityId;
    @Column(nullable = false, length = 50) private String action;
    @Column(nullable = false, length = 255) private String actor;
    @Column(columnDefinition = "TEXT") private String beforeValue;
    @Column(columnDefinition = "TEXT") private String afterValue;
    @Column(nullable = false) private LocalDateTime occurredAt;
}

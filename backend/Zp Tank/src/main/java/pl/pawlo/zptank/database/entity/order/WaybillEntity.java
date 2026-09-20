package pl.pawlo.zptank.database.entity.order;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "waybillId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "waybills")
public class WaybillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "waybill_number")
    private String waybillNumber;

    @Column(name = "issue_date")
    private LocalDate issueDate;
}

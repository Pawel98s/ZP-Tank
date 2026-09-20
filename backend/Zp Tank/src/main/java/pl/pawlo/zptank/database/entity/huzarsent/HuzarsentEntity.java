package pl.pawlo.zptank.database.entity.huzarsent;

import jakarta.persistence.*;
import lombok.*;
import pl.pawlo.zptank.database.entity.order.OrderEntity;

@Getter
@Setter
@EqualsAndHashCode(of = "huzarsentId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "huzarsent")
public class HuzarsentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", unique = true)
    private OrderEntity order;

    private String sent;
    private String ks;
    private String kr;
    private String kd;
}

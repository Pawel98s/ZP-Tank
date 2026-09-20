package pl.pawlo.zptank.database.entity.transport;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = "transportSetId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transport_sets")
public class TransportSetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tractor_id", unique = true)
    private TractorEntity tractor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trailer_id", unique = true)
    private TrailerEntity trailer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tanker_truck_id", unique = true)
    private TankerTruckEntity tankerTruck;

    @Column(name = "active", nullable = false)
    private boolean active;

}

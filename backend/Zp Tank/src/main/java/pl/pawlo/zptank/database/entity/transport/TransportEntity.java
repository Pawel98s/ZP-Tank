package pl.pawlo.zptank.database.entity.transport;

import jakarta.persistence.*;
import lombok.*;
import pl.pawlo.zptank.database.entity.order.OrderEntity;
import pl.pawlo.zptank.domain.TransportStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transports")
public class TransportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private TransportStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private DriverEntity driver;

    @OneToMany(mappedBy = "transport")
    private List<OrderEntity> orders;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "loading_terminal_id", nullable = false)
    private LoadingTerminalEntity loadingTerminal;

    @Column(name = "loading_date")
    private LocalDate loadingDate;

    @Column(name = "unloading_date")
    private LocalDate unloadingDate;

}

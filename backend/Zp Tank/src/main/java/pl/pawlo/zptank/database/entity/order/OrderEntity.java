package pl.pawlo.zptank.database.entity.order;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.pawlo.zptank.database.entity.address.DeliveryAddressEntity;
import pl.pawlo.zptank.database.entity.intermediary.IntermediaryEntity;
import pl.pawlo.zptank.database.entity.transport.TransportEntity;
import pl.pawlo.zptank.domain.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private OrderStatus status;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_request_id", nullable = false, unique = true)
    private OrderRequestEntity orderRequest;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "execution_date")
    private LocalDate executionDate;

    @Column(name = "planned_delivery_date")
    private LocalDate plannedDeliveryDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "intermediary_id", nullable = false)
    private IntermediaryEntity intermediary;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "waybill_id", unique = true, nullable = false)
    private WaybillEntity waybill;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "delivery_address_id", nullable = false)
    private DeliveryAddressEntity deliveryAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_id")
    private TransportEntity transport;
}

package pl.pawlo.zptank.database.entity.order;

import jakarta.persistence.*;
import lombok.*;
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
@EqualsAndHashCode(of = "orderId")
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

    @OneToMany(mappedBy = "order")
    private List<OrderRequestEntity> orderRequests = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "execution_date")
    private LocalDate executionDate;

    @Column(name = "planned_delivery_date")
    private LocalDate plannedDeliveryDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intermediary_id", unique = true)
    private IntermediaryEntity intermediary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waybill_id", unique = true)
    private WaybillEntity waybill;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "delivery_address_id", nullable = false)
    private DeliveryAddressEntity deliveryAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_id")
    private TransportEntity transport;
}

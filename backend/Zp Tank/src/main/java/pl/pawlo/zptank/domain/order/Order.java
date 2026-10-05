package pl.pawlo.zptank.domain.order;

import lombok.*;
import pl.pawlo.zptank.domain.OrderStatus;
import pl.pawlo.zptank.domain.address.DeliveryAddress;
import pl.pawlo.zptank.domain.intermediary.Intermediary;
import pl.pawlo.zptank.domain.transport.Transport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Order {

    private Long id;
    private OrderStatus status;
    private OrderRequest orderRequest;
    private LocalDateTime createdAt;
    private LocalDate executionDate;
    private LocalDate plannedDeliveryDate;
    private LocalDate deliveryDate;
    private Intermediary intermediary;
    private Waybill waybill;
    private DeliveryAddress deliveryAddress;
    private Transport transport;
}

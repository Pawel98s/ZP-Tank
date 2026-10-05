package pl.pawlo.zptank.api.dto.order;

import lombok.*;
import pl.pawlo.zptank.api.dto.address.DeliveryAddressDTO;
import pl.pawlo.zptank.api.dto.intermediary.IntermediaryDTO;
import pl.pawlo.zptank.api.dto.transport.TransportDTO;
import pl.pawlo.zptank.domain.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private OrderStatus status;
    private OrderRequestDTO orderRequest;
    private LocalDateTime createdAt;
    private LocalDate executionDate;
    private LocalDate plannedDeliveryDate;
    private LocalDate deliveryDate;
    private IntermediaryDTO intermediary;
    private WaybillDTO waybill;
    private DeliveryAddressDTO deliveryAddress;
    private TransportDTO transport;
}

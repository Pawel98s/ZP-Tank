package pl.pawlo.zptank.api.dto.order;

import lombok.*;
import pl.pawlo.zptank.api.dto.client.ClientDTO;
import pl.pawlo.zptank.api.dto.product.ProductDTO;
import pl.pawlo.zptank.domain.OrderRequestStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    private Long id;
    private OrderRequestStatus status;
    private LocalDateTime createdAt;
    private ClientDTO client;
    private ProductDTO product;
    private BigDecimal quantity;
    private BigDecimal price;
    private LocalDate executionDate;
    private String notes;
    private OrderDTO order;

}

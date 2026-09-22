package pl.pawlo.zptank.domain.order;

import lombok.*;
import pl.pawlo.zptank.domain.OrderRequestStatus;
import pl.pawlo.zptank.domain.client.Client;
import pl.pawlo.zptank.domain.product.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class OrderRequest {

    private Long id;
    private OrderRequestStatus status;
    private LocalDateTime createdAt;
    private Client client;
    private Product product;
    private BigDecimal quantity;
    private BigDecimal price;
    private LocalDate executionDate;
    private String notes;
    private Order order;

}

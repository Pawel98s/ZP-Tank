package pl.pawlo.zptank.domain.intermediary;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Intermediary {

    private Long id;
    private String name;
    private BigDecimal discountPrice;
}

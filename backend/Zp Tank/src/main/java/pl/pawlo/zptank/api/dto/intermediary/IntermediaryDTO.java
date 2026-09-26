package pl.pawlo.zptank.api.dto.intermediary;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntermediaryDTO {

    private Long id;
    private String name;
    private BigDecimal discountPrice;
}

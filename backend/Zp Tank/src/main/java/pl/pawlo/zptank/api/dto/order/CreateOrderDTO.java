package pl.pawlo.zptank.api.dto.order;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDTO {

    private Long orderRequestId;
    private Long intermediaryId;
    private Long deliveryAddressId;


}

package pl.pawlo.zptank.api.dto.order;


import lombok.Getter;
import lombok.Setter;
import pl.pawlo.zptank.domain.OrderRequestStatus;

@Getter
@Setter
public class UpdateOrderRequestStatusDTO {

    private OrderRequestStatus orderRequestStatus;

}

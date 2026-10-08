package pl.pawlo.zptank.api.dto.order;

import lombok.Getter;
import lombok.Setter;
import pl.pawlo.zptank.domain.OrderStatus;

@Getter
@Setter
public class UpdateOrderStatusDTO {

    private OrderStatus orderStatus;
}

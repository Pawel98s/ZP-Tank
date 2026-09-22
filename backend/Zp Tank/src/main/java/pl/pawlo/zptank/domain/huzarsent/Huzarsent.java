package pl.pawlo.zptank.domain.huzarsent;

import lombok.*;
import pl.pawlo.zptank.domain.order.Order;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Huzarsent {

    private Long id;
    private Order order;
    private String sent;
    private String ks;
    private String kr;
    private String kd;
}

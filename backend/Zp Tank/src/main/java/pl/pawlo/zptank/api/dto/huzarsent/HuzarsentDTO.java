package pl.pawlo.zptank.api.dto.huzarsent;

import lombok.*;
import pl.pawlo.zptank.api.dto.order.OrderDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HuzarsentDTO {

    private Long id;
    private OrderDTO order;
    private String sent;
    private String ks;
    private String kr;
    private String kd;
}

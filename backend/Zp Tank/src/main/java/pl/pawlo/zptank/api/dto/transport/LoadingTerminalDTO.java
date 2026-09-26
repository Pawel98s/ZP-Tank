package pl.pawlo.zptank.api.dto.transport;

import lombok.*;
import pl.pawlo.zptank.api.dto.address.AddressDTO;
import pl.pawlo.zptank.api.dto.product.ProductDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoadingTerminalDTO {

    private Long id;
    private AddressDTO address;
    private BigDecimal quantity;
    private List<ProductDTO> products = new ArrayList<>();

}

package pl.pawlo.zptank.domain.transport;

import lombok.*;
import pl.pawlo.zptank.domain.address.Address;
import pl.pawlo.zptank.domain.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class LoadingTerminal {

    private Long id;
    private Address address;
    private BigDecimal quantity;
    private List<Product> products = new ArrayList<>();

}

package pl.pawlo.zptank.domain.product;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Product {

    private Long id;
    private String name;
}

package pl.pawlo.zptank.database.entity.transport;

import jakarta.persistence.*;
import lombok.*;
import pl.pawlo.zptank.database.entity.address.AddressEntity;
import pl.pawlo.zptank.database.entity.product.ProductEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "loadingTerminalId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loading_terminals")
public class LoadingTerminalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", unique = true)
    private AddressEntity address;

    @Column(name = "quantity", precision = 19, scale = 3)
    private BigDecimal quantity;

    @ManyToMany
    @JoinTable(
            name = "loading_terminal_products",
            joinColumns = @JoinColumn(name = "loading_terminal_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> products = new ArrayList<>();

}

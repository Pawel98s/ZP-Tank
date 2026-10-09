package pl.pawlo.zptank.database.entity.transport;

import jakarta.persistence.*;
import lombok.*;
import pl.pawlo.zptank.database.entity.address.AddressEntity;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loading_terminals")
public class LoadingTerminalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", unique = true, nullable = false)
    private AddressEntity address;


}

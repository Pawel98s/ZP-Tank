package pl.pawlo.zptank.database.entity.client;

import jakarta.persistence.*;
import lombok.*;
import pl.pawlo.zptank.database.entity.address.AddressEntity;
import pl.pawlo.zptank.database.entity.address.DeliveryAddressEntity;
import pl.pawlo.zptank.database.entity.order.OrderRequestEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_address_id", unique = true, nullable = false)
    private AddressEntity companyAddress;

    @OneToMany(mappedBy = "client")
    private List<DeliveryAddressEntity> deliveryAddresses = new ArrayList<>();

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "notes")
    private String notes;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "client")
    private List<OrderRequestEntity> orderRequests = new ArrayList<>();

}

package pl.pawlo.zptank.domain.address;

import lombok.*;
import pl.pawlo.zptank.domain.client.Client;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class DeliveryAddress {

    private Long id;
    private String city;
    private String postalCode;
    private String street;
    private String houseNumber;
    private Client client;

}

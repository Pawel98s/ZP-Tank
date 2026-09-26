package pl.pawlo.zptank.api.dto.address;

import lombok.*;
import pl.pawlo.zptank.api.dto.client.ClientDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddressDTO {

    private Long id;
    private String city;
    private String postalCode;
    private String street;
    private String houseNumber;
    private ClientDTO client;

}

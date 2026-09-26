package pl.pawlo.zptank.api.dto.address;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;
    private String city;
    private String postalCode;
    private String street;
    private String houseNumber;

}

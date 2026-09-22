package pl.pawlo.zptank.domain.address;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Address {

    private Long id;
    private String city;
    private String postalCode;
    private String street;
    private String houseNumber;

}

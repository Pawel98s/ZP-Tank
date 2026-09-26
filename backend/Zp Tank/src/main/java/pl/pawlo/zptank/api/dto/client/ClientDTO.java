package pl.pawlo.zptank.api.dto.client;

import lombok.*;
import pl.pawlo.zptank.api.dto.address.AddressDTO;
import pl.pawlo.zptank.api.dto.address.DeliveryAddressDTO;
import pl.pawlo.zptank.api.dto.order.OrderRequestDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    private Long id;
    private String taxId;
    private String name;
    private AddressDTO companyAddress;
    private List<DeliveryAddressDTO> deliveryAddresses;
    private String phone;
    private String email;
    private String notes;
    private String login;
    private String password;
    private List<OrderRequestDTO> orderRequests;

}

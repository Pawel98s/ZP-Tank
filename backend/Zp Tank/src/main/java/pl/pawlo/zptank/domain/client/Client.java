package pl.pawlo.zptank.domain.client;

import lombok.*;
import pl.pawlo.zptank.domain.address.Address;
import pl.pawlo.zptank.domain.address.DeliveryAddress;
import pl.pawlo.zptank.domain.order.OrderRequest;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Client {

    private Long id;
    private String taxId;
    private String name;
    private Address companyAddress;
    private List<DeliveryAddress> deliveryAddresses;
    private String phone;
    private String email;
    private String notes;
    private String login;
    private String password;
    private List<OrderRequest> orderRequests;

}

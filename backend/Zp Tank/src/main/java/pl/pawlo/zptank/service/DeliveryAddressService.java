package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawlo.zptank.domain.address.DeliveryAddress;
import pl.pawlo.zptank.service.dao.DeliveryAddressDAO;

@Service
@AllArgsConstructor
public class DeliveryAddressService {

   private final DeliveryAddressDAO deliveryAddressDAO;

    public DeliveryAddress save(DeliveryAddress deliveryAddress){
        return deliveryAddressDAO.save(deliveryAddress);
    }

    public DeliveryAddress findById(Long id){
        return deliveryAddressDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery address not found"));
    }


    @Transactional
    public DeliveryAddress update(Long id, DeliveryAddress deliveryAddress) {
        DeliveryAddress existingDeliveryAddress = findById(id);

        DeliveryAddress updatedDeliveryAddress = DeliveryAddress.builder()
                .id(existingDeliveryAddress.getId())
                .city(deliveryAddress.getCity() != null
                        ? deliveryAddress.getCity()
                        : existingDeliveryAddress.getCity())
                .postalCode(deliveryAddress.getPostalCode() != null
                        ? deliveryAddress.getPostalCode()
                        : existingDeliveryAddress.getPostalCode())
                .street(deliveryAddress.getStreet() != null
                        ? deliveryAddress.getStreet()
                        : existingDeliveryAddress.getStreet())
                .houseNumber(deliveryAddress.getHouseNumber() != null
                        ? deliveryAddress.getHouseNumber()
                        : existingDeliveryAddress.getHouseNumber())
                .client(deliveryAddress.getClient() != null
                        ? deliveryAddress.getClient()
                        : existingDeliveryAddress.getClient())
                .build();

        return deliveryAddressDAO.update(updatedDeliveryAddress);
    }

    public void delete(Long id) {
        DeliveryAddress deliveryAddress = findById(id);
        deliveryAddressDAO.delete(deliveryAddress.getId());
    }

}

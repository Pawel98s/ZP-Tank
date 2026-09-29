package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.address.DeliveryAddress;

import java.util.Optional;

public interface DeliveryAddressDAO {

    DeliveryAddress save(DeliveryAddress deliveryAddress);

    Optional<DeliveryAddress> findById(Long id);

    DeliveryAddress update(DeliveryAddress deliveryAddress);

    void delete(Long id);

}

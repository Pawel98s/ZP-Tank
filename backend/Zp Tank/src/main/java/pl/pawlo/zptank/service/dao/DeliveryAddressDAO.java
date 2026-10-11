package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.address.DeliveryAddress;

import java.util.Optional;
import java.util.List;

public interface DeliveryAddressDAO {

    DeliveryAddress save(DeliveryAddress deliveryAddress);

    Optional<DeliveryAddress> findById(Long id);

    List<DeliveryAddress> findByClientId(Long clientId);

    boolean existsByIdAndClientId(Long addressId, Long clientId);

    DeliveryAddress update(DeliveryAddress deliveryAddress);

    void delete(Long id);

}

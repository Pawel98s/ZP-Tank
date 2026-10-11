package pl.pawlo.zptank.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.address.DeliveryAddressEntity;
import pl.pawlo.zptank.database.repository.jpa.address.DeliveryAddressJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.DeliveryAddressEntityMapper;
import pl.pawlo.zptank.domain.address.DeliveryAddress;
import pl.pawlo.zptank.service.dao.DeliveryAddressDAO;

import java.util.Optional;
import java.util.List;

@Repository
@AllArgsConstructor
public class DeliveryAddressRepository implements DeliveryAddressDAO {

    private final DeliveryAddressJpaRepository deliveryAddressJpaRepository;
    private final DeliveryAddressEntityMapper deliveryAddressEntityMapper;

    @Override
    public DeliveryAddress save(DeliveryAddress deliveryAddress) {
        DeliveryAddressEntity save = deliveryAddressJpaRepository
                .save(deliveryAddressEntityMapper.mapToEntity(deliveryAddress));
        return deliveryAddressEntityMapper.mapToDomain(save);
    }

    @Override
    public Optional<DeliveryAddress> findById(Long id) {
        return deliveryAddressJpaRepository.findById(id)
                .map(deliveryAddressEntityMapper::mapToDomain);
    }

    @Override
    public List<DeliveryAddress> findByClientId(Long clientId) {
        return deliveryAddressJpaRepository.findByClientId(clientId)
                .stream()
                .map(deliveryAddressEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public boolean existsByIdAndClientId(Long addressId, Long clientId) {
        return deliveryAddressJpaRepository.existsByIdAndClientId(addressId, clientId);
    }

    @Override
    public DeliveryAddress update(DeliveryAddress deliveryAddress) {
        DeliveryAddressEntity save = deliveryAddressJpaRepository
                .save(deliveryAddressEntityMapper.mapToEntity(deliveryAddress));
        return deliveryAddressEntityMapper.mapToDomain(save);
    }

    @Override
    public void delete(Long id) {
        deliveryAddressJpaRepository.deleteById(id);
    }
}

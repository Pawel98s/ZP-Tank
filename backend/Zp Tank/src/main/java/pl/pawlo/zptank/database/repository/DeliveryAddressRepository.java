package pl.pawlo.zptank.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.address.DeliveryAddressEntity;
import pl.pawlo.zptank.database.repository.jpa.address.DeliveryAddressJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.DeliveryAddressEntityMapper;
import pl.pawlo.zptank.domain.address.DeliveryAddress;
import pl.pawlo.zptank.service.dao.DeliveryAddressDAO;

import java.util.Optional;

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


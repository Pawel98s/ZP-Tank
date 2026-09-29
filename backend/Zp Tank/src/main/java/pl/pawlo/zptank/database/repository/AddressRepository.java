package pl.pawlo.zptank.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.api.mapper.AddressMapper;
import pl.pawlo.zptank.api.mapper.AddressMapperImpl;
import pl.pawlo.zptank.database.entity.address.AddressEntity;
import pl.pawlo.zptank.database.repository.jpa.address.AddressJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.AddressEntityMapper;
import pl.pawlo.zptank.domain.address.Address;
import pl.pawlo.zptank.service.dao.AddressDAO;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class AddressRepository implements AddressDAO {

    private final AddressJpaRepository addressJpaRepository;
    private final AddressEntityMapper addressMapper;


    @Override
    public Address save(Address address) {
        AddressEntity save = addressJpaRepository.save(addressMapper.matToEntity(address));
        return addressMapper.mapToDomain(save);
    }

    @Override
    public Optional<Address> findById(Long id) {
       return addressJpaRepository.findById(id)
                .map(addressMapper::mapToDomain);
    }

    @Override
    public Address update(Address address) {
        AddressEntity save = addressJpaRepository.save(addressMapper.matToEntity(address));
        return addressMapper.mapToDomain(save);
    }

    @Override
    public void delete(Long id) {
        addressJpaRepository.deleteById(id);
    }
}

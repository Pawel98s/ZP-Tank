package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.address.Address;

import java.util.Optional;

public interface AddressDAO {

    Address save(Address address);

    Optional<Address> findById(Long id);

    Address update(Address address);

    void delete(Long id);
}

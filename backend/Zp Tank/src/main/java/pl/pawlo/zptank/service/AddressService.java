package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.zptank.domain.address.Address;
import pl.pawlo.zptank.service.dao.AddressDAO;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressDAO addressDAO;

    public Address save(Address address) {
        return addressDAO.save(address);
    }

    public Address findById(Long id) {
        return addressDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
    }

    public Address update(Long id, Address address) {
        Address existingAddress = findById(id);

        Address updatedAddress = Address.builder()
                .id(existingAddress.getId())
                .city(address.getCity() != null
                        ? address.getCity()
                        : existingAddress.getCity())
                .postalCode(address.getPostalCode() != null
                        ? address.getPostalCode()
                        : existingAddress.getPostalCode())
                .street(address.getStreet() != null
                        ? address.getStreet()
                        : existingAddress.getStreet())
                .houseNumber(address.getHouseNumber() != null
                        ? address.getHouseNumber()
                        : existingAddress.getHouseNumber())
                .build();

        return addressDAO.update(updatedAddress);
    }

    public void delete(Long id) {
        Address address = findById(id);
        addressDAO.delete(address.getId());
    }
}

package pl.pawlo.zptank.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.address.Address;
import pl.pawlo.zptank.service.dao.AddressDAO;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressDAO addressDAO;

    @InjectMocks
    private AddressService addressService;


    @Test
    public void shouldSaveAddress() {

        Address address = Address.builder()
                .id(1L)
                .street("Main Street")
                .city("New York")
                .build();

        Mockito.when(addressDAO.save(address)).thenReturn(address);

        Address save = addressService.save(address);

        Assertions.assertThat(save).isEqualTo(address);
        Mockito.verify(addressDAO).save(address);
    }

    @Test
    public void shouldFindAddressById() {
        Address address = Address.builder()
                .id(1L)
                .street("Main Street")
                .city("New York")
                .build();

        Mockito.when(addressDAO.findById(1L)).thenReturn(Optional.of(address));

        Address result = addressService.findById(1L);

        Assertions.assertThat(result).isEqualTo(address);
        Mockito.verify(addressDAO).findById(1L);
    }

    @Test
    public void shouldUpdateAddress() {
        Address existingAddress = Address.builder()
                .id(1L)
                .street("Old Street")
                .city("Old City")
                .build();

        Address updatedAddress = Address.builder()
                .id(1L)
                .street("New Street")
                .city("New City")
                .build();

        Mockito.when(addressDAO.update(updatedAddress)).thenReturn(updatedAddress);
        Mockito.when(addressDAO.findById(1L)).thenReturn(Optional.of(existingAddress));


        Address result = addressService.update(1L,updatedAddress);

        Assertions.assertThat(result).isEqualTo(updatedAddress);
        Mockito.verify(addressDAO).update(updatedAddress);
    }

    @Test
    public void shouldDeleteAddress() {
        Long addressId = 1L;

        Address address = Address.builder()
                .id(addressId)
                .street("Main Street")
                .city("New York")
                .build();

        Mockito.when(addressDAO.findById(addressId)).thenReturn(Optional.of(address));

        addressService.delete(addressId);

        Mockito.verify(addressDAO).delete(addressId);
    }
}

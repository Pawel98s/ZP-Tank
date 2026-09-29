package pl.pawlo.zptank.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.address.DeliveryAddress;
import pl.pawlo.zptank.service.dao.DeliveryAddressDAO;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeliveryAddressServiceTest {

    @Mock
    private DeliveryAddressDAO deliveryAddressDAO;

    @InjectMocks
    private DeliveryAddressService deliveryAddressService;

    @Test
    void shouldSaveDeliveryAddress() {
        DeliveryAddress deliveryAddress = DeliveryAddress.builder()
                .id(1L)
                .street("Main Street")
                .city("New York")
                .build();

        Mockito.when(deliveryAddressDAO.save(deliveryAddress)).thenReturn(deliveryAddress);

        DeliveryAddress save = deliveryAddressService.save(deliveryAddress);

        Assertions.assertThat(save).isEqualTo(deliveryAddress);
    }

    @Test
    void shouldFindDeliveryAddressById() {
        DeliveryAddress deliveryAddress = DeliveryAddress.builder()
                .id(1L)
                .street("Main Street")
                .city("New York")
                .build();

        Mockito.when(deliveryAddressDAO.findById(1L)).thenReturn(Optional.of(deliveryAddress));

        DeliveryAddress result = deliveryAddressService.findById(1L);

        Assertions.assertThat(result).isEqualTo(deliveryAddress);
    }

    @Test
    void shouldUpdateDeliveryAddress() {
        DeliveryAddress existingAddress = DeliveryAddress.builder()
                .id(1L)
                .street("Old Street")
                .city("Old City")
                .build();

        DeliveryAddress updatedAddress = DeliveryAddress.builder()
                .id(1L)
                .street("New Street")
                .city("New City")
                .build();

        Mockito.when(deliveryAddressDAO.update(updatedAddress)).thenReturn(updatedAddress);
        Mockito.when(deliveryAddressDAO.findById(1L)).thenReturn(Optional.of(existingAddress));

        DeliveryAddress result = deliveryAddressService.update(1L, updatedAddress);

        Assertions.assertThat(result).isEqualTo(updatedAddress);
    }

    @Test
    void shouldDeleteDeliveryAddress() {
        DeliveryAddress deliveryAddress = DeliveryAddress.builder()
                .id(1L)
                .street("Main Street")
                .city("New York")
                .build();

        Mockito.when(deliveryAddressDAO.findById(1L)).thenReturn(Optional.of(deliveryAddress));

        deliveryAddressService.delete(1L);

        Mockito.verify(deliveryAddressDAO).delete(1L);
    }
}

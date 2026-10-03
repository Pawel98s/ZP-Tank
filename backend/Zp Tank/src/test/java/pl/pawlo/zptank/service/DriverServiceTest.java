package pl.pawlo.zptank.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.transport.Driver;
import pl.pawlo.zptank.service.dao.DriverDAO;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DriverServiceTest {

    @Mock
    private DriverDAO driverDAO;

    @InjectMocks
    private DriverService driverService;

    @Test
    void shouldSaveDriver() {

        Driver driver = Driver.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        Mockito.when(driverDAO.save(driver)).thenReturn(driver);

        Driver save = driverService.save(driver);

        Assertions.assertThat(save).isEqualTo(driver);
        Assertions.assertThat(save.getFirstName()).isEqualTo(driver.getFirstName());
        Assertions.assertThat(save.getLastName()).isEqualTo(driver.getLastName());
    }

    @Test
    void shouldFindDriverById() {
        Driver driver = Driver.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        Mockito.when(driverDAO.findById(1L)).thenReturn(Optional.of(driver));

        Driver result = driverService.findById(1L);

        Assertions.assertThat(result).isEqualTo(driver);
    }

    @Test
    void shouldUpdateDriver() {
        Driver existingDriver = Driver.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        Driver updatedDriver = Driver.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Smith")
                .build();

        Mockito.when(driverDAO.update(updatedDriver)).thenReturn(updatedDriver);
        Mockito.when(driverDAO.findById(1L)).thenReturn(Optional.of(existingDriver));

        Driver result = driverService.update(1L, updatedDriver);

        Assertions.assertThat(result).isEqualTo(updatedDriver);
        Assertions.assertThat(result.getFirstName()).isEqualTo(updatedDriver.getFirstName());
        Assertions.assertThat(result.getLastName()).isEqualTo(updatedDriver.getLastName());
    }

    @Test
    void shouldDeleteDriver() {
        Driver driver = Driver.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        Mockito.when(driverDAO.findById(1L)).thenReturn(Optional.of(driver));

        driverService.delete(1L);

        Mockito.verify(driverDAO, Mockito.times(1)).delete(driver.getId());
    }

    @Test
    void shouldFindAllDrivers() {
        Driver driver1 = Driver.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        Driver driver2 = Driver.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .build();

        Mockito.when(driverDAO.findAll()).thenReturn(List.of(driver1, driver2));

        List<Driver> drivers = driverService.findAll();

        Assertions.assertThat(drivers).hasSize(2);
        Assertions.assertThat(drivers.get(0).getFirstName()).isEqualTo("John");
        Assertions.assertThat(drivers.get(1).getFirstName()).isEqualTo("Jane");
    }

    @Test
    void shouldFindDriversByLastName() {
        Driver driver1 = Driver.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        Driver driver2 = Driver.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .build();

        Mockito.when(driverDAO.findByLastName("Doe")).thenReturn(List.of(driver1, driver2));

        List<Driver> drivers = driverService.findByLastName("Doe");

        Assertions.assertThat(drivers).hasSize(2);
        Assertions.assertThat(drivers.get(0).getLastName()).isEqualTo("Doe");
        Assertions.assertThat(drivers.get(1).getLastName()).isEqualTo("Doe");
    }
}

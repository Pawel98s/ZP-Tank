package pl.pawlo.zptank.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.transport.TankerTruck;
import pl.pawlo.zptank.service.dao.TankerTruckDAO;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TankerTruckServiceTest {

    @Mock
    private TankerTruckDAO tankerTruckDAO;

    @InjectMocks
    private TankerTruckService tankerTruckService;

    @Test
    void shouldSaveTankerTruck() {

        TankerTruck tankerTruck = TankerTruck.builder()
                .id(1L)
                .registrationNumber("ABC123")
                .build();

        Mockito.when(tankerTruckDAO.save(tankerTruck)).thenReturn(tankerTruck);

        TankerTruck save = tankerTruckService.save(tankerTruck);

        Assertions.assertThat(save).isEqualTo(tankerTruck);
        Assertions.assertThat(save.getRegistrationNumber()).isEqualTo(tankerTruck.getRegistrationNumber());
    }

    @Test
    void shouldFindTankerTruckById() {
        TankerTruck tankerTruck = TankerTruck.builder()
                .id(1L)
                .registrationNumber("ABC123")
                .build();

        Mockito.when(tankerTruckDAO.findById(1L)).thenReturn(Optional.of(tankerTruck));

        TankerTruck result = tankerTruckService.findById(1L);

        Assertions.assertThat(result).isEqualTo(tankerTruck);
    }

    @Test
    void shouldFindAllTankerTrucks() {
        TankerTruck tankerTruck1 = TankerTruck.builder()
                .id(1L)
                .registrationNumber("ABC123")
                .build();

        TankerTruck tankerTruck2 = TankerTruck.builder()
                .id(2L)
                .registrationNumber("DEF456")
                .build();

        Mockito.when(tankerTruckDAO.findAll()).thenReturn(List.of(tankerTruck1, tankerTruck2));

        List<TankerTruck> result = tankerTruckService.findAll();

        Assertions.assertThat(result).containsExactlyInAnyOrder(tankerTruck1, tankerTruck2);
    }

    @Test
    void shouldUpdateTankerTruck() {
        TankerTruck existingTankerTruck = TankerTruck.builder()
                .id(1L)
                .registrationNumber("ABC123")
                .build();

        TankerTruck updatedTankerTruck = TankerTruck.builder()
                .id(1L)
                .registrationNumber("XYZ789")
                .build();

        Mockito.when(tankerTruckDAO.findById(1L)).thenReturn(Optional.of(existingTankerTruck));
        Mockito.when(tankerTruckDAO.update(updatedTankerTruck)).thenReturn(updatedTankerTruck);

        TankerTruck result = tankerTruckService.update(1L, updatedTankerTruck);

        Assertions.assertThat(result).isEqualTo(updatedTankerTruck);
    }

    @Test
    void shouldDeleteTankerTruck() {
        TankerTruck existingTankerTruck = TankerTruck.builder()
                .id(1L)
                .registrationNumber("ABC123")
                .build();

        Mockito.when(tankerTruckDAO.findById(1L)).thenReturn(Optional.of(existingTankerTruck));

        tankerTruckService.delete(1L);

        Mockito.verify(tankerTruckDAO).delete(1L);
    }
}

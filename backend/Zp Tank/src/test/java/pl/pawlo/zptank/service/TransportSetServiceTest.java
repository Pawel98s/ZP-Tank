package pl.pawlo.zptank.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.transport.TankerTruck;
import pl.pawlo.zptank.domain.transport.Tractor;
import pl.pawlo.zptank.domain.transport.Trailer;
import pl.pawlo.zptank.domain.transport.TransportSet;
import pl.pawlo.zptank.service.dao.TransportSetDAO;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TransportSetServiceTest {

    @Mock
    private TransportSetDAO transportSetDAO;

    @Mock
    private TrailerService trailerService;

    @Mock
    private TractorService tractorService;

    @Mock
    private TankerTruckService tankerTruckService;

    @InjectMocks
    private TransportSetService transportSetService;

    @Test
    void shouldSaveTransportSet() {
        TransportSet transportSet = TransportSet.builder()
                .id(1L)
                .active(true)
                .tractor(Tractor.builder()
                        .id(2L)
                        .registrationNumber("DEF456")
                        .build())
                .trailer(Trailer.builder()
                        .id(3L)
                        .registrationNumber("GHI789")
                        .build())
                .build();

        Mockito.when(transportSetDAO.save(transportSet)).thenReturn(transportSet);

        TransportSet save = transportSetService.save(transportSet);

        Assertions.assertThat(save).isEqualTo(transportSet);
    }

    @Test
    void shouldRejectTankerWithTractor(){
        TransportSet transportSet = TransportSet.builder()
                .id(1L)
                .active(true)
                .tractor(Tractor.builder()
                        .id(2L)
                        .registrationNumber("DEF456")
                        .build())
                .tankerTruck(TankerTruck.builder()
                        .id(4L)
                        .registrationNumber("JKL012")
                        .build())
                .build();

        Assertions.assertThatThrownBy(() -> transportSetService.save(transportSet))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Transport set must contain either a tanker truck or a tractor with a trailer");

        Mockito.verify(transportSetDAO, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldFindTransportSetById() {
        TransportSet transportSet = TransportSet.builder()
                .id(1L)
                .active(true)
                .tractor(Tractor.builder()
                        .id(2L)
                        .registrationNumber("DEF456")
                        .build())
                .trailer(Trailer.builder()
                        .id(3L)
                        .registrationNumber("GHI789")
                        .build())
                .build();

        Mockito.when(transportSetDAO.findById(1L)).thenReturn(Optional.of(transportSet));

        TransportSet result = transportSetService.findById(1L);

        Assertions.assertThat(result).isEqualTo(transportSet);
    }

    @Test
    void shouldUpdateTransportSet() {
        TransportSet existingTransportSet = TransportSet.builder()
                .id(1L)
                .active(true)
                .tractor(Tractor.builder()
                        .id(2L)
                        .registrationNumber("DEF456")
                        .build())
                .trailer(Trailer.builder()
                        .id(3L)
                        .registrationNumber("GHI789")
                        .build())
                .build();

        TransportSet updatedTransportSet = TransportSet.builder()
                .id(1L)
                .active(false)
                .tractor(Tractor.builder()
                        .id(2L)
                        .registrationNumber("DEF456")
                        .build())
                .trailer(Trailer.builder()
                        .id(3L)
                        .registrationNumber("GHI789")
                        .build())
                .build();

        Mockito.when(transportSetDAO.findById(1L)).thenReturn(java.util.Optional.of(existingTransportSet));
        Mockito.when(transportSetDAO.update(updatedTransportSet)).thenReturn(updatedTransportSet);

        TransportSet result = transportSetService.update(1L, updatedTransportSet);

        Assertions.assertThat(result).isEqualTo(updatedTransportSet);
    }

    @Test
    void shouldRejectUpdateWithTankerAndTractor() {
        TransportSet existingTransportSet = TransportSet.builder()
                .id(1L)
                .active(true)
                .tractor(Tractor.builder()
                        .id(2L)
                        .registrationNumber("DEF456")
                        .build())
                .trailer(Trailer.builder()
                        .id(3L)
                        .registrationNumber("GHI789")
                        .build())
                .build();

        TransportSet updatedTransportSet = TransportSet.builder()
                .id(1L)
                .active(false)
                .tractor(Tractor.builder()
                        .id(2L)
                        .registrationNumber("DEF456")
                        .build())
                .tankerTruck(TankerTruck.builder()
                        .id(4L)
                        .registrationNumber("JKL012")
                        .build())
                .build();

        Mockito.when(transportSetDAO.findById(1L)).thenReturn(java.util.Optional.of(existingTransportSet));

        Assertions.assertThatThrownBy(() -> transportSetService.update(1L, updatedTransportSet))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Transport set must contain either a tanker truck or a tractor with a trailer");

        Mockito.verify(transportSetDAO, Mockito.never()).update(Mockito.any());
    }

    @Test
    void shouldDeleteTransportSet() {
        TransportSet transportSet = TransportSet.builder()
                .id(1L)
                .active(true)
                .tractor(Tractor.builder()
                        .id(2L)
                        .registrationNumber("DEF456")
                        .build())
                .trailer(Trailer.builder()
                        .id(3L)
                        .registrationNumber("GHI789")
                        .build())
                .build();

        Mockito.when(transportSetDAO.findById(1L)).thenReturn(java.util.Optional.of(transportSet));

        transportSetService.delete(1L);

        Mockito.verify(transportSetDAO).delete(1L);
    }

    @Test
    void shouldFindAllTransportSets() {
        TransportSet transportSet1 = TransportSet.builder()
                .id(1L)
                .active(true)
                .tractor(Tractor.builder()
                        .id(2L)
                        .registrationNumber("DEF456")
                        .build())
                .trailer(Trailer.builder()
                        .id(3L)
                        .registrationNumber("GHI789")
                        .build())
                .build();

        TransportSet transportSet2 = TransportSet.builder()
                .id(4L)
                .active(false)
                .tankerTruck(TankerTruck.builder()
                        .id(5L)
                        .registrationNumber("JKL012")
                        .build())
                .build();

        Mockito.when(transportSetDAO.findAll()).thenReturn(java.util.List.of(transportSet1, transportSet2));

        java.util.List<TransportSet> result = transportSetService.findAll();

        Assertions.assertThat(result).containsExactlyInAnyOrder(transportSet1, transportSet2);
    }

    @Test
    void shouldCreateTransportSetWithTractorAndTrailer() {
        TransportSet transportSet = TransportSet.builder()
                .active(true)
                .tractor(Tractor.builder()
                        .id(2L)
                        .registrationNumber("DEF456")
                        .build())
                .trailer(Trailer.builder()
                        .id(3L)
                        .registrationNumber("GHI789")
                        .build())
                .build();

        Mockito.when(tractorService.findById(2L)).thenReturn(transportSet.getTractor());
        Mockito.when(trailerService.findById(3L)).thenReturn(transportSet.getTrailer());
        Mockito.when(transportSetDAO.save(transportSet)).thenReturn(transportSet);

        TransportSet result = transportSetService.create(transportSet);

        Assertions.assertThat(result).isEqualTo(transportSet);
    }
}

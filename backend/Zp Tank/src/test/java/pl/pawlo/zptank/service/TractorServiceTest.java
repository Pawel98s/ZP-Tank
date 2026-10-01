package pl.pawlo.zptank.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.transport.Tractor;
import pl.pawlo.zptank.service.dao.TractorDAO;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TractorServiceTest {

    @Mock
    private TractorDAO tractorDAO;

    @InjectMocks
    private TractorService tractorService;

    @Test
    void shouldSaveTractor() {
        Tractor tractor = Tractor.builder()
                .id(1L)
                .registrationNumber("ABC123")
                .build();

        Mockito.when(tractorDAO.save(tractor)).thenReturn(tractor);

        Tractor save = tractorService.save(tractor);

        Assertions.assertThat(save).isEqualTo(tractor);
    }

    @Test
    void shouldFindTractorById() {
        Tractor tractor = Tractor.builder()
                .id(1L)
                .registrationNumber("ABC123")
                .build();

        Mockito.when(tractorDAO.findById(1L)).thenReturn(Optional.of(tractor));

        Tractor result = tractorService.findById(1L);

        Assertions.assertThat(result).isEqualTo(tractor);
    }

    @Test
    void shouldUpdateTractor() {
        Tractor existingTractor = Tractor.builder()
                .id(1L)
                .registrationNumber("ABC123")
                .build();

        Tractor updatedTractor = Tractor.builder()
                .id(1L)
                .registrationNumber("XYZ789")
                .build();

        Mockito.when(tractorDAO.findById(1L)).thenReturn(Optional.of(existingTractor));
        Mockito.when(tractorDAO.update(updatedTractor)).thenReturn(updatedTractor);

        Tractor result = tractorService.update(1L,updatedTractor);

        Assertions.assertThat(result).isEqualTo(updatedTractor);
    }

    @Test
    void shouldFindAllTractors() {
        Tractor tractor1 = Tractor.builder()
                .id(1L)
                .registrationNumber("ABC123")
                .build();

        Tractor tractor2 = Tractor.builder()
                .id(2L)
                .registrationNumber("XYZ789")
                .build();

        Mockito.when(tractorDAO.findAll()).thenReturn(java.util.List.of(tractor1, tractor2));

        List<Tractor> tractors = tractorService.findAll();

        Assertions.assertThat(tractors).hasSize(2);
        Assertions.assertThat(tractors.get(0).getRegistrationNumber()).isEqualTo("ABC123");
        Assertions.assertThat(tractors.get(1).getRegistrationNumber()).isEqualTo("XYZ789");
    }

    @Test
    void shouldDeleteTractor() {
        Tractor tractor = Tractor.builder()
                .id(1L)
                .registrationNumber("ABC123")
                .build();

        Mockito.when(tractorDAO.findById(1L)).thenReturn(Optional.of(tractor));

        tractorService.delete(1L);

        Mockito.verify(tractorDAO, Mockito.times(1)).delete(1L);
    }
}

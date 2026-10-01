package pl.pawlo.zptank.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.transport.Trailer;
import pl.pawlo.zptank.service.dao.TrailerDAO;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TrailerServiceTest {

    @Mock
    private TrailerDAO trailerDAO;

    @InjectMocks
    private TrailerService trailerService;

    @Test
    void shouldSaveTrailer() {
        Trailer trailer = Trailer.builder()
                .id(1L)
                .registrationNumber("TR123")
                .build();

        Mockito.when(trailerDAO.save(trailer)).thenReturn(trailer);

        Trailer save = trailerService.save(trailer);

        Assertions.assertThat(save).isEqualTo(trailer);
    }

    @Test
    void shouldFindTrailerById() {
        Trailer trailer = Trailer.builder()
                .id(1L)
                .registrationNumber("TR123")
                .build();

        Mockito.when(trailerDAO.findById(1L)).thenReturn(Optional.of(trailer));

        Trailer result = trailerService.findById(1L);

        Assertions.assertThat(result).isEqualTo(trailer);
    }

    @Test
    void shouldFindAllTrailers() {
        Trailer trailer1 = Trailer.builder()
                .id(1L)
                .registrationNumber("TR123")
                .build();

        Trailer trailer2 = Trailer.builder()
                .id(2L)
                .registrationNumber("TR456")
                .build();

        Mockito.when(trailerDAO.findAll()).thenReturn(List.of(trailer1, trailer2));

        List<Trailer> result = trailerService.findAll();

        Assertions.assertThat(result).containsExactlyInAnyOrder(trailer1, trailer2);
    }

    @Test
    void shouldUpdateTrailer() {
        Trailer existingTrailer = Trailer.builder()
                .id(1L)
                .registrationNumber("TR123")
                .build();

        Trailer updatedTrailer = Trailer.builder()
                .id(1L)
                .registrationNumber("TR456")
                .build();

        Mockito.when(trailerDAO.findById(1L)).thenReturn(Optional.of(existingTrailer));
        Mockito.when(trailerDAO.update(updatedTrailer)).thenReturn(updatedTrailer);

        Trailer result = trailerService.update(1L, updatedTrailer);

        Assertions.assertThat(result).isEqualTo(updatedTrailer);
    }

    @Test
    void shouldDeleteTrailer() {
        Trailer trailer = Trailer.builder()
                .id(1L)
                .registrationNumber("TR123")
                .build();

        Mockito.when(trailerDAO.findById(1L)).thenReturn(Optional.of(trailer));

        trailerService.delete(1L);

        Mockito.verify(trailerDAO).delete(1L);
    }
}

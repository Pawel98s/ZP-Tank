package pl.pawlo.zptank.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.intermediary.Intermediary;
import pl.pawlo.zptank.service.dao.IntermediaryDAO;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class IntermediaryServiceTest {

    @Mock
    private IntermediaryDAO intermediaryDAO;

    @InjectMocks
    private IntermediaryService intermediaryService;

    @Test
    void shouldSaveIntermediary(){

        Intermediary intermediary = Intermediary.builder()
                .id(1L)
                .name("Kloa")
                .build();

        Mockito.when(intermediaryDAO.save(intermediary)).thenReturn(intermediary);

        Intermediary save = intermediaryService.save(intermediary);

        Assertions.assertThat(save).isEqualTo(intermediary);
    }

    @Test
    void shouldUpdateIntermediary(){
        Intermediary intermediary = Intermediary.builder()
                .id(1L)
                .name("Kloa")
                .build();

        Intermediary intermediaryUpdate = Intermediary.builder()
                .id(1L)
                .name("Nowe")
                .build();

        Mockito.when(intermediaryDAO.update(intermediaryUpdate)).thenReturn(intermediaryUpdate);
        Mockito.when(intermediaryDAO.findById(1L)).thenReturn(Optional.of(intermediary));

        Intermediary update = intermediaryService.update(intermediary.getId(), intermediaryUpdate);

        Assertions.assertThat(update).isEqualTo(intermediaryUpdate);
    }

    @Test
    void shouldDeleteIntermediary(){
        Intermediary intermediary = Intermediary.builder()
                .id(1L)
                .name("Kloa")
                .build();


        Mockito.when(intermediaryDAO.findById(1L)).thenReturn(Optional.of(intermediary));

        intermediaryService.delete(1L);

        Mockito.verify(intermediaryDAO, Mockito.times(1)).delete(intermediary.getId());
    }

    @Test
    void shouldFindIntermediaryById(){
        Intermediary intermediary = Intermediary.builder()
                .id(1L)
                .name("Kloa")
                .build();

        Mockito.when(intermediaryDAO.findById(1L)).thenReturn(Optional.of(intermediary));

        Intermediary byId = intermediaryService.findById(1L);

        Assertions.assertThat(byId).isEqualTo(intermediary);
    }

    @Test
    void shouldFindAllIntermediaries(){
        Intermediary intermediary1 = Intermediary.builder()
                .id(1L)
                .name("Kloa")
                .build();

        Intermediary intermediary2 = Intermediary.builder()
                .id(1L)
                .name("Nowe")
                .build();

        Mockito.when(intermediaryDAO.findAll()).thenReturn(List.of(intermediary1, intermediary2));

        List<Intermediary> all = intermediaryService.findAll();

        Assertions.assertThat(all).isEqualTo(List.of(intermediary1, intermediary2));
    }
}

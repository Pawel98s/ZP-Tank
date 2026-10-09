package pl.pawlo.zptank.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.address.Address;
import pl.pawlo.zptank.domain.transport.LoadingTerminal;
import pl.pawlo.zptank.service.dao.LoadingTerminalDAO;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LoadingTerminalServiceTest {

    @Mock
    private LoadingTerminalDAO loadingTerminalDAO;

    @InjectMocks
    private LoadingTerminalService loadingTerminalService;

    @Test
    void shouldSaveLoadingTerminal() {

        LoadingTerminal loadingTerminal = LoadingTerminal.builder()
                .id(1L)
                .build();

        Mockito.when(loadingTerminalDAO.save(loadingTerminal)).thenReturn(loadingTerminal);

        LoadingTerminal save = loadingTerminalService.save(loadingTerminal);

        Assertions.assertThat(save).isEqualTo(loadingTerminal);
    }

    @Test
    void shouldFindLoadingTerminalById() {

        LoadingTerminal loadingTerminal = LoadingTerminal.builder()
                .id(1L)
                .build();

        Mockito.when(loadingTerminalDAO.findById(1L)).thenReturn(Optional.of(loadingTerminal));

        LoadingTerminal found = loadingTerminalService.findById(1L);

        Assertions.assertThat(found).isEqualTo(loadingTerminal);
    }

    @Test
    void shouldFindAllLoadingTerminals() {

        LoadingTerminal loadingTerminal1 = LoadingTerminal.builder()
                .id(1L)
                .build();

        LoadingTerminal loadingTerminal2 = LoadingTerminal.builder()
                .id(2L)
                .build();

        Mockito.when(loadingTerminalDAO.findAll()).thenReturn(java.util.List.of(loadingTerminal1, loadingTerminal2));

        List<LoadingTerminal> found = loadingTerminalService.findAll();

        Assertions.assertThat(found).containsExactlyInAnyOrder(loadingTerminal1, loadingTerminal2);
    }

    @Test
    void shouldDeleteLoadingTerminalById() {

        LoadingTerminal loadingTerminal = LoadingTerminal.builder()
                .id(1L)
                .build();

        Mockito.when(loadingTerminalDAO.findById(1L)).thenReturn(Optional.of(loadingTerminal));

        loadingTerminalService.delete(1L);

        Mockito.verify(loadingTerminalDAO).delete(1L);
    }

    @Test
    void shouldUpdateLoadingTerminal() {
        LoadingTerminal loadingTerminal = LoadingTerminal.builder()
                .id(1L)
                .address(Address.builder()
                        .city("Old City")
                        .postalCode("12345")
                        .street("New Street")
                        .houseNumber("1A")
                        .build())
                .build();

        LoadingTerminal existingLoadingTerminal = LoadingTerminal.builder()
                .id(1L)
                .address(Address.builder()
                        .city("New City")
                        .postalCode("12345678")
                        .street("New Street")
                        .houseNumber("2A")
                        .build())
                .build();

        Mockito.when(loadingTerminalDAO.findById(1L)).thenReturn(Optional.of(existingLoadingTerminal));
        Mockito.when(loadingTerminalDAO.update(loadingTerminal)).thenReturn(loadingTerminal);

        LoadingTerminal updated = loadingTerminalService.update(1L, loadingTerminal);

        Assertions.assertThat(updated.getAddress().getCity()).isEqualTo("Old City");
    }
}

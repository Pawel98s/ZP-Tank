package pl.pawlo.zptank.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;
import pl.pawlo.zptank.domain.client.Client;
import pl.pawlo.zptank.service.dao.ClientDAO;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientDAO clientDAO;

    @InjectMocks
    private ClientService clientService;

    @Test
    void shouldSaveClient(){
        Client client = Client.builder()
                .id(1L)
                .taxId("2131231")
                .name("CCO")
                .build();

        Mockito.when(clientDAO.save(client))
                .thenReturn(client);

        Client result = clientService.save(client);

        Assertions.assertThat(result).isEqualTo(client);
        Mockito.verify(clientDAO).save(client);
    }

    @Test
    void shouldFindAllClients(){
        Client client1 =Client.builder()
                .id(1L)
                .taxId("123123")
                .name("Coco")
                .build();

        Client client2 =Client.builder()
                .id(2L)
                .taxId("12312345")
                .name("CocoLO")
                .build();

        clientService.save(client1);
        clientService.save(client2);

        Mockito.when(clientDAO.findAll()).thenReturn(List.of(client1,client2));

        List<Client> clients = clientService.findAll();

        Assertions.assertThat(clients).hasSize(2);
        Assertions.assertThat(clients.get(0).getName()).isEqualTo("Coco");
    }

    @Test
    void shouldFindClientById(){

        Client client = Client.builder()
                .id(1L)
                .taxId("123123")
                .name("Coco")
                .build();

        Mockito.when(clientDAO.findById(1L)).thenReturn(Optional.ofNullable(client));

        Client clientById = clientService.findById(1L);

        Assertions.assertThat(clientById).isEqualTo(client);
        Assertions.assertThat(clientById.getName()).isEqualTo("Coco");

    }

    @Test
    void shouldUpdateClient() {
        Client client = Client.builder()
                .id(1L)
                .taxId("123123")
                .name("Coco")
                .build();

        Client updatedClient = Client.builder()
                .taxId("44444")
                .name("CocoNew")
                .build();

        Client expectedClient = Client.builder()
                .id(1L)
                .taxId("44444")
                .name("CocoNew")
                .build();

        Mockito.when(clientDAO.findById(1L)).thenReturn(Optional.of(client));

        Mockito.when(clientDAO.update(expectedClient)).thenReturn(expectedClient);

        Client result = clientService.update(1L, updatedClient);

        Assertions.assertThat(result).isEqualTo(expectedClient);
        Assertions.assertThat(result.getName()).isEqualTo("CocoNew");
    }


    @Test
    void shouldDeleteClient() {
        Long clientId = 1L;

        Client client = Client.builder()
                .id(clientId)
                .name("Coco")
                .taxId("123123")
                .build();

        Mockito.when(clientDAO.findById(clientId))
                .thenReturn(Optional.of(client));

        clientService.delete(clientId);

        Mockito.verify(clientDAO).delete(clientId);
    }
}

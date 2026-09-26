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
}

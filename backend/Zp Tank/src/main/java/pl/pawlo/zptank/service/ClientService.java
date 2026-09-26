package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.zptank.api.dto.client.ClientDTO;
import pl.pawlo.zptank.domain.client.Client;
import pl.pawlo.zptank.service.dao.ClientDAO;

@Service
@AllArgsConstructor
public class ClientService {

    ClientDAO clientDAO;

    public Client save(Client client){
       return clientDAO.save(client);
    }
}

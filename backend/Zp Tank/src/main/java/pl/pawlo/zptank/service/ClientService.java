package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.zptank.api.dto.client.ClientDTO;
import pl.pawlo.zptank.domain.client.Client;
import pl.pawlo.zptank.service.dao.ClientDAO;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    ClientDAO clientDAO;

    public Client save(Client client){
       return clientDAO.save(client);
    }

    public List<Client> findAll(){
        return clientDAO.findAll();
    }

    public Client findById(Long id){
        return clientDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }
}

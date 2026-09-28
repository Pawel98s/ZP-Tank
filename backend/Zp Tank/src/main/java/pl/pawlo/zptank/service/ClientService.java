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

    public Client update(Long id, Client client) {

        Client existingClient = findById(id);

        Client updatedClient = Client.builder()
                .id(existingClient.getId())
                .taxId(client.getTaxId() != null
                        ? client.getTaxId()
                        : existingClient.getTaxId())
                .name(client.getName() != null
                        ? client.getName()
                        : existingClient.getName())
                .companyAddress(client.getCompanyAddress() != null
                        ? client.getCompanyAddress()
                        : existingClient.getCompanyAddress())
                .deliveryAddresses(client.getDeliveryAddresses() != null
                        ? client.getDeliveryAddresses()
                        : existingClient.getDeliveryAddresses())
                .phone(client.getPhone() != null
                        ? client.getPhone()
                        : existingClient.getPhone())
                .email(client.getEmail() != null
                        ? client.getEmail()
                        : existingClient.getEmail())
                .notes(client.getNotes() != null
                        ? client.getNotes()
                        : existingClient.getNotes())
                .login(client.getLogin() != null
                        ? client.getLogin()
                        : existingClient.getLogin())
                .password(client.getPassword() != null
                        ? client.getPassword()
                        : existingClient.getPassword())
                .orderRequests(existingClient.getOrderRequests())
                .build();

        return clientDAO.update(id, updatedClient);
    }

    public void delete(Long id) {
        Client client = findById(id);
        clientDAO.delete(client.getId());
    }
}

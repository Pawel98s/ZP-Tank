package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.client.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDAO {

    Client save(Client client);

    List<Client> findAll();

    Optional<Client> findById(Long id);

    Client update(Long id, Client client);

    void delete(Long id);

}

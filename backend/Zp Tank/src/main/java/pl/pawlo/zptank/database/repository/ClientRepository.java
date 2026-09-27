package pl.pawlo.zptank.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.client.ClientEntity;
import pl.pawlo.zptank.database.repository.jpa.client.ClientJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.ClientEntityMapper;
import pl.pawlo.zptank.domain.client.Client;
import pl.pawlo.zptank.service.dao.ClientDAO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ClientRepository implements ClientDAO {

    private final ClientJpaRepository clientJpaRepository;
    private final ClientEntityMapper clientEntityMapper;


    @Override
    public Client save(Client client) {
        ClientEntity save = clientJpaRepository.save(clientEntityMapper.mapToEntity(client));
        return clientEntityMapper.mapToDomain(save);
    }

    @Override
    public List<Client> findAll() {
        return clientJpaRepository.findAll()
                .stream()
                .map(clientEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientJpaRepository.findById(id)
                .map(clientEntityMapper::mapToDomain);
    }


}

package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.transport.Transport;

import java.util.List;
import java.util.Optional;

public interface TransportDAO {

    Transport save(Transport transport);

    Optional<Transport> findById(Long id);

    List<Transport> findAll();

    Transport update(Transport transport);

    void delete(Long id);
}

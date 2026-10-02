package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.transport.TransportSet;

import java.util.List;
import java.util.Optional;

public interface TransportSetDAO {

    TransportSet save(TransportSet transportSet);

    Optional<TransportSet>  findById(Long id);

    List<TransportSet> findAll();

    TransportSet update(TransportSet transportSet);

    void delete(Long id);
}

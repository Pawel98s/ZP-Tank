package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.transport.Trailer;

import java.util.List;
import java.util.Optional;

public interface TrailerDAO {

    Trailer save(Trailer trailer);

    Optional<Trailer> findById(Long id);

    List<Trailer> findAll();

    Trailer update(Trailer trailer);

    void delete(Long id);
}

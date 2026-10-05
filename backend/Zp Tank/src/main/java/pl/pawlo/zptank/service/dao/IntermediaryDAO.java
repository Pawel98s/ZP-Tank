package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.intermediary.Intermediary;

import java.util.List;
import java.util.Optional;

public interface IntermediaryDAO {

    Intermediary save(Intermediary intermediary);

    Optional<Intermediary> findById(Long id);

    List<Intermediary> findAll();

    Intermediary update(Intermediary intermediary);

    void delete(Long id);
}

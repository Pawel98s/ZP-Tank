package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.transport.Tractor;

import java.util.List;
import java.util.Optional;

public interface TractorDAO {

    Tractor save(Tractor tractor);

    Optional<Tractor> findById(Long id);

    List<Tractor> findAll();

    Tractor update(Tractor tractor);

    void delete(Long id);



}

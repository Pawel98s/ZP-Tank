package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.transport.TankerTruck;

import java.util.List;
import java.util.Optional;

public interface TankerTruckDAO {

    TankerTruck save(TankerTruck tankerTruck);

    Optional<TankerTruck> findById(Long id);

    List<TankerTruck> findAll();

    TankerTruck update(TankerTruck tankerTruck);

    void delete(Long id);
}

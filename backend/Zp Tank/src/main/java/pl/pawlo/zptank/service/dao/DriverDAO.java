package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.transport.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverDAO {

    Driver save(Driver driver);

    Optional<Driver> findById(Long id);

    List<Driver> findAll();

    Driver update(Driver driver);

    void delete(Long id);

    List<Driver> findByLastName(String lastName);
}

package pl.pawlo.zptank.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.transport.DriverEntity;
import pl.pawlo.zptank.database.repository.jpa.transport.DriverJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.DriverEntityMapper;
import pl.pawlo.zptank.domain.transport.Driver;
import pl.pawlo.zptank.service.dao.DriverDAO;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DriverRepository implements DriverDAO {

    private final DriverJpaRepository driverJpaRepository;
    private final DriverEntityMapper driverEntityMapper;

    @Override
    public Driver save(Driver driver) {
        DriverEntity save = driverJpaRepository.save(driverEntityMapper.mapToEntity(driver));
        return driverEntityMapper.mapToDomain(save);
    }

    @Override
    public Optional<Driver> findById(Long id) {
        return driverJpaRepository.findById(id)
                .map(driverEntityMapper::mapToDomain);
    }


    @Override
    public List<Driver> findAll() {
       return driverJpaRepository.findAll()
                .stream()
                .map(driverEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public Driver update(Driver driver) {
        DriverEntity save = driverJpaRepository.save(driverEntityMapper.mapToEntity(driver));
        return driverEntityMapper.mapToDomain(save);
    }

    @Override
    public void delete(Long id) {
        driverJpaRepository.deleteById(id);
    }

    @Override
    public List<Driver> findByLastName(String lastName) {
        return driverJpaRepository.findByLastNameContainingIgnoreCase(lastName)
                .stream()
                .map(driverEntityMapper::mapToDomain)
                .toList();
    }


}

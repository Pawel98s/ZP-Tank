package pl.pawlo.zptank.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.transport.TankerTruckEntity;
import pl.pawlo.zptank.database.repository.jpa.transport.TankerTruckJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.TankerTruckEntityMapper;
import pl.pawlo.zptank.domain.transport.TankerTruck;
import pl.pawlo.zptank.service.dao.TankerTruckDAO;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TankerTruckRepository implements TankerTruckDAO {

    private final TankerTruckJpaRepository tankerTruckJpaRepository;
    private final TankerTruckEntityMapper tankerTruckEntityMapper;


    @Override
    public TankerTruck save(TankerTruck tankerTruck) {
        TankerTruckEntity save = tankerTruckJpaRepository.save(tankerTruckEntityMapper.mapToEntity(tankerTruck));
        return tankerTruckEntityMapper.mapToDomain(save);
    }

    @Override
    public Optional<TankerTruck> findById(Long id) {
        return tankerTruckJpaRepository.findById(id)
                .map(tankerTruckEntityMapper::mapToDomain);
    }

    @Override
    public List<TankerTruck> findAll() {
        return tankerTruckJpaRepository.findAll()
                .stream()
                .map(tankerTruckEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public TankerTruck update(TankerTruck tankerTruck) {
        TankerTruckEntity save = tankerTruckJpaRepository.save(tankerTruckEntityMapper.mapToEntity(tankerTruck));
        return tankerTruckEntityMapper.mapToDomain(save);
    }

    @Override
    public void delete(Long id) {
        tankerTruckJpaRepository.deleteById(id);
    }
}

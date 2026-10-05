package pl.pawlo.zptank.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.order.WaybillEntity;
import pl.pawlo.zptank.database.repository.jpa.order.WaybillJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.WaybillEntityMapper;
import pl.pawlo.zptank.domain.order.Waybill;
import pl.pawlo.zptank.service.dao.WaybillDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class WaybillRepository implements WaybillDAO {

    private final WaybillJpaRepository waybillJpaRepository;
    private final WaybillEntityMapper waybillEntityMapper;

    @Override
    public Waybill save(Waybill waybill) {
        WaybillEntity entity = waybillEntityMapper.mapToEntity(waybill);

        WaybillEntity savedEntity = waybillJpaRepository.save(entity);

        return waybillEntityMapper.mapToDomain(savedEntity);
    }

    @Override
    public Optional<Waybill> findById(Long id) {
        return waybillJpaRepository.findById(id)
                .map(waybillEntityMapper::mapToDomain);
    }

    @Override
    public List<Waybill> findAll() {
        return waybillJpaRepository.findAll()
                .stream()
                .map(waybillEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public Waybill update(Waybill waybill) {
        WaybillEntity entity = waybillEntityMapper.mapToEntity(waybill);

        WaybillEntity updatedEntity = waybillJpaRepository.save(entity);

        return waybillEntityMapper.mapToDomain(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        waybillJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Waybill> findLastByIssueDateBetween(LocalDate startDate, LocalDate endDate) {
        return waybillJpaRepository
                .findTopByIssueDateBetweenOrderByIdDesc(startDate, endDate)
                .map(waybillEntityMapper::mapToDomain);
    }

    @Override
    public void lockWaybillNumberGeneration(String lockKey) {
        waybillJpaRepository.lockWaybillNumberGeneration(lockKey);
    }
}

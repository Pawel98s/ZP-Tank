package pl.pawlo.zptank.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.transport.TransportEntity;
import pl.pawlo.zptank.database.repository.jpa.transport.TransportJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.TransportEntityMapper;
import pl.pawlo.zptank.domain.TransportStatus;
import pl.pawlo.zptank.domain.transport.Transport;
import pl.pawlo.zptank.service.dao.TransportDAO;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
@AllArgsConstructor
public class TransportRepository implements TransportDAO {

    private final TransportJpaRepository transportJpaRepository;
    private final TransportEntityMapper transportEntityMapper;

    @Override
    public Transport save(Transport transport) {
        TransportEntity save = transportJpaRepository.save(transportEntityMapper.mapToEntity(transport));
        return transportEntityMapper.mapToDomain(save);
    }

    @Override
    public Optional<Transport> findById(Long id) {
        return transportJpaRepository.findById(id)
                .map(transportEntityMapper::mapToDomain);
    }

    @Override
    public List<Transport> findAll() {
       return transportJpaRepository.findAll()
                .stream()
                .map(transportEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public Transport update(Transport transport) {
        TransportEntity save = transportJpaRepository.save(transportEntityMapper.mapToEntity(transport));
        return transportEntityMapper.mapToDomain(save);
    }

    @Override
    public void delete(Long id) {
        transportJpaRepository.deleteById(id);
    }

    @Override
    public List<Transport> findByStatus(TransportStatus status) {
        return transportJpaRepository.findByStatus(status)
                .stream()
                .map(transportEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public List<Transport> findByDriverId(Long driverId) {
        return transportJpaRepository.findByDriverId(driverId)
                .stream()
                .map(transportEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public boolean existsDriverScheduleConflict(
            Long driverId,
            LocalDate loadingDate,
            LocalDate unloadingDate,
            Long excludedTransportId
    ) {
        return transportJpaRepository.existsDriverScheduleConflict(
                driverId, loadingDate, unloadingDate, excludedTransportId
        );
    }

    @Override
    public Page<Transport> search(TransportStatus status, Long driverId, Long vehicleId, Long terminalId,
                                  String text, LocalDate from, LocalDate to, Pageable pageable) {
        return transportJpaRepository.search(status, driverId, vehicleId, terminalId, text, from, to, pageable)
                .map(transportEntityMapper::mapToDomain);
    }

    @Override
    public long countByLoadingDate(LocalDate date) {
        return transportJpaRepository.countByLoadingDate(date);
    }

    @Override
    public long countWithoutDriverOrVehicle() {
        return transportJpaRepository.countWithoutDriverOrVehicle();
    }

    @Override
    public long countByStatus(TransportStatus status) {
        return transportJpaRepository.countByStatus(status);
    }

    @Override
    public long count() {
        return transportJpaRepository.count();
    }
}

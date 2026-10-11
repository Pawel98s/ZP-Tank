package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.TransportStatus;
import pl.pawlo.zptank.domain.transport.Transport;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransportDAO {

    Transport save(Transport transport);

    Optional<Transport> findById(Long id);

    List<Transport> findAll();

    Transport update(Transport transport);

    void delete(Long id);

    List<Transport> findByStatus(TransportStatus status);

    List<Transport> findByDriverId(Long driverId);

    boolean existsDriverScheduleConflict(
            Long driverId,
            LocalDate loadingDate,
            LocalDate unloadingDate,
            Long excludedTransportId
    );

    Page<Transport> search(TransportStatus status, Long driverId, Long vehicleId, Long terminalId,
                           String text, LocalDate from, LocalDate to, Pageable pageable);

    long countByLoadingDate(LocalDate date);
    long countWithoutDriverOrVehicle();
    long countByStatus(TransportStatus status);
    long count();
}

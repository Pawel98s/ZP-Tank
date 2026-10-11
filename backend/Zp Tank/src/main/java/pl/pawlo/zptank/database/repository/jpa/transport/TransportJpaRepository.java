package pl.pawlo.zptank.database.repository.jpa.transport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.pawlo.zptank.database.entity.transport.TransportEntity;
import pl.pawlo.zptank.domain.TransportStatus;

import java.util.List;

@Repository
public interface TransportJpaRepository extends JpaRepository<TransportEntity,Long> {

    @Query("""
            select distinct t from TransportEntity t
            left join t.driver d
            left join d.transportSet s
            left join s.tractor tractor
            left join s.trailer trailer
            left join s.tankerTruck tanker
            where (:status is null or t.status = :status)
              and (:driverId is null or d.id = :driverId)
              and (:vehicleId is null or tractor.id = :vehicleId or trailer.id = :vehicleId or tanker.id = :vehicleId)
              and (:terminalId is null or t.loadingTerminal.id = :terminalId)
              and (:fromDate is null or t.loadingDate >= :fromDate)
              and (:toDate is null or t.loadingDate <= :toDate)
              and (:text is null or lower(concat(coalesce(d.firstName, ''), ' ', coalesce(d.lastName, ''))) like lower(concat('%', :text, '%'))
                   or lower(coalesce(tractor.registrationNumber, '')) like lower(concat('%', :text, '%'))
                   or lower(coalesce(trailer.registrationNumber, '')) like lower(concat('%', :text, '%'))
                   or lower(coalesce(tanker.registrationNumber, '')) like lower(concat('%', :text, '%')))
            """)
    Page<TransportEntity> search(
            @Param("status") TransportStatus status,
            @Param("driverId") Long driverId,
            @Param("vehicleId") Long vehicleId,
            @Param("terminalId") Long terminalId,
            @Param("text") String text,
            @Param("fromDate") java.time.LocalDate fromDate,
            @Param("toDate") java.time.LocalDate toDate,
            Pageable pageable
    );

    long countByLoadingDate(java.time.LocalDate loadingDate);
    long countByStatus(TransportStatus status);

    @Query("select count(t) from TransportEntity t where t.driver is null or t.driver.transportSet is null or t.driver.transportSet.active = false")
    long countWithoutDriverOrVehicle();

    List<TransportEntity> findByStatus(TransportStatus status);

    List<TransportEntity> findByDriverId(Long driverId);

    @Query("""
            select count(t) > 0
            from TransportEntity t
            where t.driver.id = :driverId
              and t.status <> pl.pawlo.zptank.domain.TransportStatus.CANCELLED
              and t.loadingDate < :unloadingDate
              and t.unloadingDate > :loadingDate
              and (:excludedTransportId is null or t.id <> :excludedTransportId)
            """)
    boolean existsDriverScheduleConflict(
            @Param("driverId") Long driverId,
            @Param("loadingDate") java.time.LocalDate loadingDate,
            @Param("unloadingDate") java.time.LocalDate unloadingDate,
            @Param("excludedTransportId") Long excludedTransportId
    );

}

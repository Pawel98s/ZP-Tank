package pl.pawlo.zptank.database.repository.jpa.transport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.pawlo.zptank.database.entity.transport.TransportEntity;
import pl.pawlo.zptank.domain.TransportStatus;

import java.util.List;

@Repository
public interface TransportJpaRepository extends JpaRepository<TransportEntity,Long> {

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

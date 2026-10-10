package pl.pawlo.zptank.database.repository.jpa.transport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.transport.TransportEntity;
import pl.pawlo.zptank.domain.TransportStatus;

import java.util.List;

@Repository
public interface TransportJpaRepository extends JpaRepository<TransportEntity,Long> {

    List<TransportEntity> findByStatus(TransportStatus status);

    List<TransportEntity> findByDriverId(Long driverId);

}

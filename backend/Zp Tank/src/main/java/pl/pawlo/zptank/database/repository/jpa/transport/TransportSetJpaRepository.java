package pl.pawlo.zptank.database.repository.jpa.transport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.transport.TransportSetEntity;

@Repository
public interface TransportSetJpaRepository extends JpaRepository<TransportSetEntity,Long> {



}

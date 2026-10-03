package pl.pawlo.zptank.database.repository.jpa.transport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.transport.DriverEntity;

import java.util.List;

@Repository
public interface DriverJpaRepository extends JpaRepository<DriverEntity,Long> {

    List<DriverEntity> findByLastNameContainingIgnoreCase(String lastName);



}

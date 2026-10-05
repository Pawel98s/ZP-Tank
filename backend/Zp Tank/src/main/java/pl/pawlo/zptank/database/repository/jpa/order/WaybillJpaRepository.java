package pl.pawlo.zptank.database.repository.jpa.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.order.WaybillEntity;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WaybillJpaRepository extends JpaRepository<WaybillEntity,Long> {


    Optional<WaybillEntity> findTopByIssueDateBetweenOrderByIdDesc(LocalDate startDate, LocalDate endDate);

    @Query(
            value = "SELECT pg_advisory_xact_lock(hashtext(:lockKey))",
            nativeQuery = true)
    void lockWaybillNumberGeneration(@Param("lockKey") String lockKey);

}

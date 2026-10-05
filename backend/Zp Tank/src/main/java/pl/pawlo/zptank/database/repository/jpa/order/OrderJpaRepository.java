package pl.pawlo.zptank.database.repository.jpa.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.order.OrderEntity;
import pl.pawlo.zptank.domain.OrderStatus;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity,Long> {


    List<OrderEntity> findByStatus(OrderStatus status);

    List<OrderEntity> findByOrderRequestClientId(Long clientId);

    List<OrderEntity> findByIntermediaryId(Long intermediaryId);

    List<OrderEntity> findByTransportId(Long transportId);

    List<OrderEntity> findByTransportIsNull();

    List<OrderEntity> findByExecutionDate(LocalDate executionDate);

    List<OrderEntity> findByExecutionDateBetween(LocalDate from, LocalDate to);

}

package pl.pawlo.zptank.database.repository.jpa.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.order.OrderRequestEntity;
import pl.pawlo.zptank.domain.OrderRequestStatus;

import java.util.List;

@Repository
public interface OrderRequestJpaRepository extends JpaRepository<OrderRequestEntity,Long> {

    List<OrderRequestEntity> findByStatus(OrderRequestStatus status);



}

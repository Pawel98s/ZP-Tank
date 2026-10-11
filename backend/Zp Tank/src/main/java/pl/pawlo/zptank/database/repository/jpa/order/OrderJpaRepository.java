package pl.pawlo.zptank.database.repository.jpa.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.pawlo.zptank.database.entity.order.OrderEntity;
import pl.pawlo.zptank.domain.OrderStatus;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity,Long> {

    @Query("""
            select o from OrderEntity o
            join o.orderRequest r
            join r.client c
            left join o.waybill w
            where (:status is null or o.status = :status)
              and (:clientId is null or c.id = :clientId)
              and (:waybill is null or lower(w.waybillNumber) like lower(concat('%', :waybill, '%')))
              and (:text is null or lower(w.waybillNumber) like lower(concat('%', :text, '%'))
                   or lower(c.name) like lower(concat('%', :text, '%')))
              and (:fromDate is null or o.executionDate >= :fromDate)
              and (:toDate is null or o.executionDate <= :toDate)
            """)
    Page<OrderEntity> search(
            @Param("status") OrderStatus status,
            @Param("clientId") Long clientId,
            @Param("waybill") String waybill,
            @Param("text") String text,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            Pageable pageable
    );

    @Query("select count(o) from OrderEntity o where o.deliveryDate is not null and o.plannedDeliveryDate is not null and o.deliveryDate <= o.plannedDeliveryDate")
    long countOnTimeDeliveries();

    @Query("select count(o) from OrderEntity o where o.deliveryDate is null and o.plannedDeliveryDate < :today")
    long countOverdue(@Param("today") LocalDate today);

    long countByDeliveryDateIsNotNull();
    long countByStatus(OrderStatus status);

    List<OrderEntity> findByStatus(OrderStatus status);

    boolean existsByOrderRequestId(Long orderRequestId);

    List<OrderEntity> findByOrderRequestClientId(Long clientId);

    List<OrderEntity> findByIntermediaryId(Long intermediaryId);

    List<OrderEntity> findByTransportId(Long transportId);

    List<OrderEntity> findByTransportIsNull();

    List<OrderEntity> findByExecutionDate(LocalDate executionDate);

    List<OrderEntity> findByExecutionDateBetween(LocalDate from, LocalDate to);

}

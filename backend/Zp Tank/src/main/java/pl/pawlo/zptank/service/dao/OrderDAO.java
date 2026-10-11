package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.OrderStatus;
import pl.pawlo.zptank.domain.order.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderDAO {

    Order save(Order order);

    Optional<Order> findById(Long id);

    boolean existsByOrderRequestId(Long orderRequestId);

    List<Order> findAll();

    Order update(Order order);

    void delete(Long id);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByClientId(Long clientId);

    List<Order> findByIntermediaryId(Long intermediaryId);

    List<Order> findByTransportId(Long transportId);

    List<Order> findWithoutTransport();

    List<Order> findByExecutionDate(LocalDate date);

    List<Order> findByExecutionDateBetween(LocalDate from, LocalDate to);

    Page<Order> search(OrderStatus status, Long clientId, String waybill, String text,
                       LocalDate from, LocalDate to, Pageable pageable);

    long countOnTimeDeliveries();
    long countOverdue(LocalDate today);
    long countDelivered();
    long countByStatus(OrderStatus status);
    long count();

}

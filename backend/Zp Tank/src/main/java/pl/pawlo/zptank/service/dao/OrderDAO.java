package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findAll();

    Order update(Order order);

    void delete(Long id);

}

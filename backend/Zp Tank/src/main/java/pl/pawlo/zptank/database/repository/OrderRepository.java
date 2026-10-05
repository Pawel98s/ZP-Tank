package pl.pawlo.zptank.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.order.OrderEntity;
import pl.pawlo.zptank.database.repository.jpa.order.OrderJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.OrderEntityMapper;
import pl.pawlo.zptank.domain.OrderStatus;
import pl.pawlo.zptank.domain.order.Order;
import pl.pawlo.zptank.service.dao.OrderDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrderRepository implements OrderDAO {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderEntityMapper orderEntityMapper;


    @Override
    public Order save(Order order) {
        OrderEntity save = orderJpaRepository.save(orderEntityMapper.mapToEntity(order));
        return orderEntityMapper.mapToDomain(save);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderJpaRepository.findById(id)
                .map(orderEntityMapper::mapToDomain);
    }

    @Override
    public List<Order> findAll() {
        return orderJpaRepository.findAll()
                .stream()
                .map(orderEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public Order update(Order order) {
        OrderEntity save = orderJpaRepository.save(orderEntityMapper.mapToEntity(order));
        return orderEntityMapper.mapToDomain(save);
    }

    @Override
    public void delete(Long id) {
        orderJpaRepository.deleteById(id);
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return orderJpaRepository.findByStatus(status)
                .stream()
                .map(orderEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public List<Order> findByClientId(Long clientId) {
        return orderJpaRepository.findByOrderRequestClientId(clientId)
                .stream()
                .map(orderEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public List<Order> findByIntermediaryId(Long intermediaryId) {
        return orderJpaRepository.findByIntermediaryId(intermediaryId)
                .stream()
                .map(orderEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public List<Order> findByTransportId(Long transportId) {
        return orderJpaRepository.findByTransportId(transportId)
                .stream()
                .map(orderEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public List<Order> findWithoutTransport() {
        return orderJpaRepository.findByTransportIsNull()
                .stream()
                .map(orderEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public List<Order> findByExecutionDate(LocalDate date) {
        return orderJpaRepository.findByExecutionDate(date)
                .stream()
                .map(orderEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public List<Order> findByExecutionDateBetween(
            LocalDate from,
            LocalDate to
    ) {
        return orderJpaRepository.findByExecutionDateBetween(from, to)
                .stream()
                .map(orderEntityMapper::mapToDomain)
                .toList();
    }
}

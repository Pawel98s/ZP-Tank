package pl.pawlo.zptank.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.order.OrderRequestEntity;
import pl.pawlo.zptank.database.repository.jpa.order.OrderRequestJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.OrderRequestEntityMapper;
import pl.pawlo.zptank.domain.OrderRequestStatus;
import pl.pawlo.zptank.domain.order.OrderRequest;
import pl.pawlo.zptank.service.dao.OrderRequestDAO;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrderRequestRepository implements OrderRequestDAO {

    private final OrderRequestJpaRepository orderRequestJpaRepository;
    private final OrderRequestEntityMapper orderRequestEntityMapper;


    @Override
    public OrderRequest save(OrderRequest orderRequest) {
        OrderRequestEntity save = orderRequestJpaRepository.save(orderRequestEntityMapper.mapToEntity(orderRequest));
        return orderRequestEntityMapper.mapToDomain(save);
    }

    @Override
    public Optional<OrderRequest> findById(Long id) {
        return orderRequestJpaRepository.findById(id)
                .map(orderRequestEntityMapper::mapToDomain);
    }

    @Override
    public List<OrderRequest> findAll() {
        return orderRequestJpaRepository.findAll()
                .stream()
                .map(orderRequestEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public OrderRequest update(OrderRequest orderRequest) {
        OrderRequestEntity save = orderRequestJpaRepository.save(orderRequestEntityMapper.mapToEntity(orderRequest));
        return orderRequestEntityMapper.mapToDomain(save);
    }

    @Override
    public void delete(Long id) {
        orderRequestJpaRepository.deleteById(id);
    }

    @Override
    public List<OrderRequest> findByStatus(OrderRequestStatus status) {
        return orderRequestJpaRepository.findByStatus(status)
                .stream()
                .map(orderRequestEntityMapper::mapToDomain)
                .toList();
    }
}

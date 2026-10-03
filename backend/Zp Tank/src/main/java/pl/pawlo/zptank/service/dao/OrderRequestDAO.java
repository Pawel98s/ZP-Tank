package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.OrderRequestStatus;
import pl.pawlo.zptank.domain.order.OrderRequest;

import java.util.List;
import java.util.Optional;

public interface OrderRequestDAO {

    OrderRequest save(OrderRequest orderRequest);

    Optional<OrderRequest> findById(Long id);

    List<OrderRequest> findAll();

    OrderRequest update(OrderRequest orderRequest);

    void delete(Long id);

    List<OrderRequest> findByStatus(OrderRequestStatus status);


}

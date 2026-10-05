package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.zptank.domain.order.Order;
import pl.pawlo.zptank.service.dao.OrderDAO;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderDAO orderDAO;

    public Order save(Order order) {
       return orderDAO.save(order);
    }

    public Order findById(Long id){
      return orderDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
    }

    public List<Order> findAll(){
        return orderDAO.findAll();
    }

    public void deleteById(Long id){
        Order order = findById(id);
        orderDAO.delete(order.getId());
    }
}

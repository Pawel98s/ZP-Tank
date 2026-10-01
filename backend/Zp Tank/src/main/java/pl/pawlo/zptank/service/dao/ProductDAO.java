package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {

    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    Product update(Product product);

    void delete(Long id);
}

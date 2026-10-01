package pl.pawlo.zptank.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.product.ProductEntity;
import pl.pawlo.zptank.database.repository.jpa.product.ProductJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.ProductEntityMapper;
import pl.pawlo.zptank.domain.product.Product;
import pl.pawlo.zptank.service.dao.ProductDAO;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProductRepository implements ProductDAO {

     private final ProductJpaRepository productJpaRepository;
     private final ProductEntityMapper productEntityMapper;


    @Override
    public Product save(Product product) {
        ProductEntity save = productJpaRepository.save(productEntityMapper.mapToEntity(product));
        return productEntityMapper.mapToDomain(save);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id)
                .map(productEntityMapper::mapToDomain);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll()
                .stream()
                .map(productEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public Product update(Product product) {
        ProductEntity save = productJpaRepository.save(productEntityMapper.mapToEntity(product));
        return productEntityMapper.mapToDomain(save);
    }

    @Override
    public void delete(Long id) {
        productJpaRepository.deleteById(id);
    }
}



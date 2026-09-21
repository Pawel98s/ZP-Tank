package pl.pawlo.zptank.database.repository.jpa.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.product.ProductEntity;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity,Long> {


}

package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawlo.zptank.domain.product.Product;
import pl.pawlo.zptank.service.dao.ProductDAO;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductDAO productDAO;

    public Product save(Product product) {
        return productDAO.save(product);
    }

    public Product findById(Long id) {
        return productDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }


    @Transactional
    public Product update(Long id, Product product) {
        Product existingProduct = findById(id);

        Product updatedProduct = Product.builder()
                .id(existingProduct.getId())
                .name(product.getName() != null
                        ? product.getName()
                        : existingProduct.getName())
                .build();

        return productDAO.update(updatedProduct);
    }

    public List<Product> findAll() {
        return productDAO.findAll();
    }

    public void delete(Long id) {
        Product product = findById(id);
        productDAO.delete(product.getId());
    }



}

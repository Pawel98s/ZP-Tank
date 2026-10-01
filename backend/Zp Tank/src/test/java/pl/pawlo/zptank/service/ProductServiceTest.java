package pl.pawlo.zptank.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.product.Product;
import pl.pawlo.zptank.service.dao.ProductDAO;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductDAO productDAO;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldSaveProduct() {

        Product product = Product.builder()
                .id(1L)
                .name("Product 1")
                .build();

        Mockito.when(productDAO.save(product)).thenReturn(product);

        Product save = productService.save(product);

        Assertions.assertThat(save).isEqualTo(product);
        Assertions.assertThat(save.getName()).isEqualTo(product.getName());
    }

    @Test
    void shouldFindProductById() {
        Product product = Product.builder()
                .id(1L)
                .name("Product 1")
                .build();

        Mockito.when(productDAO.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.findById(1L);

        Assertions.assertThat(result).isEqualTo(product);
    }

    @Test
    void shouldUpdateProduct() {
        Product existingProduct = Product.builder()
                .id(1L)
                .name("Old Product")
                .build();

        Product updatedProduct = Product.builder()
                .id(1L)
                .name("Updated Product")
                .build();

        Mockito.when(productDAO.findById(1L)).thenReturn(Optional.of(existingProduct));
        Mockito.when(productDAO.update(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.update(1L, updatedProduct);

        Assertions.assertThat(result).isEqualTo(updatedProduct);
    }

    @Test
    void shouldFindAllProducts() {
        Product product1 = Product.builder()
                .id(1L)
                .name("Product 1")
                .build();

        Product product2 = Product.builder()
                .id(2L)
                .name("Product 2")
                .build();

        Mockito.when(productDAO.findAll()).thenReturn(List.of(product1, product2));

        List<Product> products = productService.findAll();

        Assertions.assertThat(products).hasSize(2);
        Assertions.assertThat(products.get(0).getName()).isEqualTo("Product 1");
        Assertions.assertThat(products.get(1).getName()).isEqualTo("Product 2");
    }

    @Test
    void shouldDeleteProduct() {
        Product product = Product.builder()
                .id(1L)
                .name("Product 1")
                .build();

        Mockito.when(productDAO.findById(1L)).thenReturn(Optional.of(product));

        productService.delete(1L);

        Mockito.verify(productDAO, Mockito.times(1)).delete(1L);
    }
}

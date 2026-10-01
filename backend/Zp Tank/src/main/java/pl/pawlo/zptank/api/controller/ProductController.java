package pl.pawlo.zptank.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.product.ProductDTO;
import pl.pawlo.zptank.api.mapper.ProductMapper;
import pl.pawlo.zptank.domain.product.Product;
import pl.pawlo.zptank.service.ProductService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDTO save(@RequestBody ProductDTO productDTO) {
        Product product = productMapper.mapToDomain(productDTO);
        Product save = productService.save(product);
        return productMapper.mapToDTO(save);
    }

    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return productMapper.mapToDTO(product);
    }

    @GetMapping
    public List<ProductDTO> findAll() {
        List<Product> products = productService.findAll();
        return products.stream()
                .map(productMapper::mapToDTO)
                .toList();
    }

    @PatchMapping("/{id}")
    public ProductDTO update(@PathVariable Long id,
                             @RequestBody ProductDTO productDTO) {
        Product product = productMapper.mapToDomain(productDTO);
        Product update = productService.update(id, product);
        return productMapper.mapToDTO(update);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

}

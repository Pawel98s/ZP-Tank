package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.product.ProductDTO;
import pl.pawlo.zptank.domain.product.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product mapToDomain(final ProductDTO productDTO);

    ProductDTO mapToDTO(final Product product);
}

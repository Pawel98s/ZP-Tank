package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.product.ProductEntity;
import pl.pawlo.zptank.domain.product.Product;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductEntityMapper {

    Product mapToDomain(final ProductEntity productEntity);

    ProductEntity mapToEntity(final Product product);
}

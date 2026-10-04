package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.order.OrderRequestEntity;
import pl.pawlo.zptank.domain.order.OrderRequest;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                ClientEntityMapper.class
        })
public interface OrderRequestEntityMapper {

    OrderRequest mapToDomain(OrderRequestEntity orderRequestEntity);

    OrderRequestEntity mapToEntity(OrderRequest orderRequest);
}

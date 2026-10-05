package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.order.OrderEntity;
import pl.pawlo.zptank.domain.order.Order;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {OrderRequestEntityMapper.class})
public interface OrderEntityMapper {

    Order mapToDomain(final OrderEntity orderEntity);

    OrderEntity mapToEntity(final Order order);
}

package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.order.OrderEntity;
import pl.pawlo.zptank.domain.order.Order;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {OrderRequestEntityMapper.class,
        DeliveryAddressEntityMapper.class, TransportReferenceMapper.class})
public interface OrderEntityMapper {

    @Mapping(
            target = "transport",
            source = "transport",
            qualifiedByName = "toDomainReference"
    )
    Order mapToDomain(final OrderEntity orderEntity);

    @Mapping(
            target = "transport",
            source = "transport",
            qualifiedByName = "toEntityReference"
    )
    OrderEntity mapToEntity(final Order order);
}

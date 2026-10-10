package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.pawlo.zptank.api.dto.order.OrderDTO;
import pl.pawlo.zptank.domain.order.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "transport", ignore = true)
    Order mapToDomain(final OrderDTO orderDTO);

    @Mapping(target = "transport", ignore = true)
    OrderDTO mapToDTO(final Order order);
}



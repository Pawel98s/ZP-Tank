package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.order.OrderDTO;
import pl.pawlo.zptank.domain.order.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order mapToDomain(final OrderDTO orderDTO);

    OrderDTO mapToDTO(final Order order);
}



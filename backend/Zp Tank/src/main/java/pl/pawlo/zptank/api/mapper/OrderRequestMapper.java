package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.order.OrderRequestDTO;
import pl.pawlo.zptank.domain.order.OrderRequest;

@Mapper(componentModel = "spring")
public interface OrderRequestMapper {

    OrderRequest mapToDomain(OrderRequestDTO orderRequestDTO);

    OrderRequestDTO mapToDTO(OrderRequest orderRequest);
}

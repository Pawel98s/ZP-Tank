package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.order.WaybillDTO;
import pl.pawlo.zptank.domain.order.Waybill;

@Mapper(componentModel = "spring")
public interface WaybillMapper {

    Waybill mapToDomain(final WaybillDTO waybillDTO);

    WaybillDTO mapToDTO(final Waybill waybill);
}

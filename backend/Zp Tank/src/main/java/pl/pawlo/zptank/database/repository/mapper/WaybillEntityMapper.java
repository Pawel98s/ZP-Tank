package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.order.WaybillEntity;
import pl.pawlo.zptank.domain.order.Waybill;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WaybillEntityMapper {

    Waybill mapToDomain(final WaybillEntity waybillEntity);

    WaybillEntity mapToEntity(final Waybill waybill);
}

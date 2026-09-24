package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.transport.TankerTruckEntity;
import pl.pawlo.zptank.domain.transport.TankerTruck;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TankerTruckEntityMapper {

    TankerTruck mapToDomain(final TankerTruckEntity tankerTruckEntity);

    TankerTruckEntity mapToEntity(final TankerTruck tankerTruck);
}

package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.transport.TankerTruckDTO;
import pl.pawlo.zptank.domain.transport.TankerTruck;

@Mapper(componentModel = "spring")
public interface TankerTruckMapper {

    TankerTruck mapToDomain(final TankerTruckDTO tankerTruckDTO);

    TankerTruckDTO mapToDTO(final TankerTruck tankerTruck);
}

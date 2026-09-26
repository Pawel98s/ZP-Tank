package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.transport.DriverDTO;
import pl.pawlo.zptank.domain.transport.Driver;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    Driver mapToDomain(final DriverDTO driverDTO);

    DriverDTO mapToDTO(final Driver driver);
}

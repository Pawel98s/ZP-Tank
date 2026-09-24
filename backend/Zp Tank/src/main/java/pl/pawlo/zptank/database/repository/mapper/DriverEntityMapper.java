package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.transport.DriverEntity;
import pl.pawlo.zptank.domain.transport.Driver;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DriverEntityMapper {

    Driver mapToDomain(final DriverEntity driverEntity);

    DriverEntity mapToEntity(final Driver driver);
}

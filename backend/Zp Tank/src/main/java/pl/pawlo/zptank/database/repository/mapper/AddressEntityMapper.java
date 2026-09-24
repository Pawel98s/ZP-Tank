package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.address.AddressEntity;
import pl.pawlo.zptank.domain.address.Address;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressEntityMapper {

    Address mapToDomain(final AddressEntity addressEntity);

    AddressEntity matToEntity(final Address address);
}

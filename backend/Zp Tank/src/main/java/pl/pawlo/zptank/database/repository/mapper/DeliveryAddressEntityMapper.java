package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.address.DeliveryAddressEntity;
import pl.pawlo.zptank.domain.address.DeliveryAddress;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeliveryAddressEntityMapper {

    DeliveryAddress mapToDomain(final DeliveryAddressEntity deliveryAddressEntity);

    DeliveryAddressEntity mapToEntity(final  DeliveryAddress deliveryAddress);

}

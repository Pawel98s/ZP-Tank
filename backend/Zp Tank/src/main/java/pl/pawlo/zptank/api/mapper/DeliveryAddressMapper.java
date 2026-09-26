package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.address.DeliveryAddressDTO;
import pl.pawlo.zptank.domain.address.DeliveryAddress;

@Mapper(componentModel = "spring")
public interface DeliveryAddressMapper {

    DeliveryAddress mapToDomain(final DeliveryAddressDTO deliveryAddressDTO);

    DeliveryAddressDTO mapToDTO(final  DeliveryAddress deliveryAddress);

}

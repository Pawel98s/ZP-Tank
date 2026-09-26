package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.address.AddressDTO;
import pl.pawlo.zptank.domain.address.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address mapToDomain(final AddressDTO addressDTO);

    AddressDTO matToDTO(final Address address);
}

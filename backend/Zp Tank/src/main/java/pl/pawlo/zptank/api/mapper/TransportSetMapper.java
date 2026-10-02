package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.pawlo.zptank.api.dto.transport.TransportSetDTO;
import pl.pawlo.zptank.domain.transport.TransportSet;

@Mapper(componentModel = "spring")
public interface TransportSetMapper {

    TransportSet mapToDomain(final TransportSetDTO transportSetDTO);

    @Mapping(
            target = "displayName",
            expression = "java(buildDisplayName(transportSet))"
    )
    TransportSetDTO mapToDTO(final TransportSet transportSet);

    default String buildDisplayName(TransportSet transportSet) {

        if (transportSet.getTankerTruck() != null) {
            return transportSet.getTankerTruck().getRegistrationNumber();
        }

        return transportSet.getTractor().getRegistrationNumber()
                + " / "
                + transportSet.getTrailer().getRegistrationNumber();
    }
}
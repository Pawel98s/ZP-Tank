package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.transport.TransportSetDTO;
import pl.pawlo.zptank.domain.transport.TransportSet;

@Mapper(componentModel = "spring")
public interface TransportSetMapper {

    TransportSet mapToDomain(final TransportSetDTO transportSetDTO);

    TransportSetDTO mapToDTO(final TransportSet transportSet);
}

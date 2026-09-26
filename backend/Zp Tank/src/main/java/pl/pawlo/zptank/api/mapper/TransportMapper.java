package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.transport.TransportDTO;
import pl.pawlo.zptank.domain.transport.Transport;

@Mapper(componentModel = "spring")
public interface TransportMapper {

    Transport mapToDomain(final TransportDTO transportDTO);

    TransportDTO mapToDTO(final Transport transport);
}

package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.transport.TractorDTO;
import pl.pawlo.zptank.api.dto.transport.TrailerDTO;
import pl.pawlo.zptank.domain.transport.Tractor;
import pl.pawlo.zptank.domain.transport.Trailer;

@Mapper(componentModel = "spring")
public interface TrailerMapper {

    Trailer mapToDomain(final TrailerDTO trailerDTO);

    TrailerDTO mapToDTO(final Trailer trailer);
}

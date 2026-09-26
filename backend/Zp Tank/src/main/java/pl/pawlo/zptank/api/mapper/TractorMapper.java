package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.transport.TractorDTO;
import pl.pawlo.zptank.domain.transport.Tractor;

@Mapper(componentModel = "spring")
public interface TractorMapper {

    Tractor mapToDomain(final TractorDTO tractorDTO);

    TractorDTO mapToDTO(final Tractor tractor);
}

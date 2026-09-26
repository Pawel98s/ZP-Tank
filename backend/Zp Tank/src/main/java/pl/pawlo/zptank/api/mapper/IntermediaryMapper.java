package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.intermediary.IntermediaryDTO;
import pl.pawlo.zptank.domain.intermediary.Intermediary;

@Mapper(componentModel = "spring")
public interface IntermediaryMapper {

    Intermediary mapToDomain(final IntermediaryDTO intermediaryDTO);

    IntermediaryDTO mapToDTO(Intermediary intermediary);
}

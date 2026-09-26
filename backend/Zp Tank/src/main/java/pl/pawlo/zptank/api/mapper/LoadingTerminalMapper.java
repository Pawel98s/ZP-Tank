package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.transport.LoadingTerminalDTO;
import pl.pawlo.zptank.domain.transport.LoadingTerminal;

@Mapper(componentModel = "spring")
public interface LoadingTerminalMapper {

    LoadingTerminal mapToDomain(final LoadingTerminalDTO loadingTerminalDTO);

    LoadingTerminalDTO mapToDTO(final LoadingTerminal loadingTerminal);
}

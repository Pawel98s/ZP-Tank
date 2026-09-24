package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.transport.LoadingTerminalEntity;
import pl.pawlo.zptank.domain.transport.LoadingTerminal;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoadingTerminalEntityMapper {

    LoadingTerminal mapToDomain(final LoadingTerminalEntity loadingTerminalEntity);

    LoadingTerminalEntity mapToEntity(final LoadingTerminal loadingTerminal);
}

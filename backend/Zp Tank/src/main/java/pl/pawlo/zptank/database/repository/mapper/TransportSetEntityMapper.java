package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.transport.TransportSetEntity;
import pl.pawlo.zptank.domain.transport.TransportSet;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransportSetEntityMapper {

    TransportSet mapToDomain(final TransportSetEntity transportSetEntity);

    TransportSetEntity mapToEntity(final TransportSet transportSet);
}

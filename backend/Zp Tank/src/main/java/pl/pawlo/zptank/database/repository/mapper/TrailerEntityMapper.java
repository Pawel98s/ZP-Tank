package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.transport.TractorEntity;
import pl.pawlo.zptank.domain.transport.Tractor;
import pl.pawlo.zptank.domain.transport.Trailer;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TrailerEntityMapper {

    Trailer mapToDomain(final TractorEntity tractorEntity);

    TractorEntity mapToEntity(final Tractor tractor);
}

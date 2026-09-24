package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.huzarsent.HuzarsentEntity;
import pl.pawlo.zptank.domain.huzarsent.Huzarsent;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HuzarsentEntityMapper {

    Huzarsent mapToDomain(final HuzarsentEntity huzarsentEntity);

    HuzarsentEntity mapToEntity(final Huzarsent huzarsent);
}

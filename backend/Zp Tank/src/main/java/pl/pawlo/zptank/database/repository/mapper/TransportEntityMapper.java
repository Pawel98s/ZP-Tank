package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.transport.TransportEntity;
import pl.pawlo.zptank.domain.transport.Transport;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE , uses = {OrderEntityMapper.class})
public interface TransportEntityMapper {


    Transport mapToDomain(final TransportEntity transportEntity);

    TransportEntity mapToEntity(final Transport transport);
}

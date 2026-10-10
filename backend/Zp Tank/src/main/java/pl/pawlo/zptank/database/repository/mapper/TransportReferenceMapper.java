package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import pl.pawlo.zptank.database.entity.transport.TransportEntity;
import pl.pawlo.zptank.domain.transport.Transport;

@Mapper(componentModel = "spring")
public interface TransportReferenceMapper {

    @Named("toDomainReference")
    default Transport toDomainReference(TransportEntity entity) {
        if (entity == null) {
            return null;
        }

        return Transport.builder()
                .id(entity.getId())
                .build();
    }

    @Named("toEntityReference")
    default TransportEntity toEntityReference(Transport transport) {
        if (transport == null) {
            return null;
        }

        return TransportEntity.builder()
                .id(transport.getId())
                .build();
    }
}

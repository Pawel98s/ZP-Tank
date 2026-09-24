package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.client.ClientEntity;
import pl.pawlo.zptank.domain.client.Client;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientEntityMapper {

    Client mapToDomain(final ClientEntity clientEntity);

    ClientEntity mapToEntity(final Client client);
}

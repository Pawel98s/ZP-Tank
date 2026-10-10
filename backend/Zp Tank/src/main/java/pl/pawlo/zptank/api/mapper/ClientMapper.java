package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.client.ClientDTO;
import pl.pawlo.zptank.domain.client.Client;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface ClientMapper {

    Client mapToDomain(final ClientDTO clientDTO);

    ClientDTO mapToDTO(final Client client);
}

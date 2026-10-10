package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.huzarsent.HuzarsentDTO;
import pl.pawlo.zptank.domain.huzarsent.Huzarsent;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface HuzarsentMapper {

    Huzarsent mapToDomain(final HuzarsentDTO huzarsentDTO);

    HuzarsentDTO mapToDTO(final Huzarsent huzarsent);
}

package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.company.CompanyDTO;
import pl.pawlo.zptank.domain.company.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company mapToDomain(final CompanyDTO companyDTO);

    CompanyDTO mapToDTO(final Company company);
}

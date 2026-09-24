package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.company.CompanyEntity;
import pl.pawlo.zptank.domain.company.Company;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyEntityMapper {

    Company mapToDomain(final CompanyEntity companyEntity);

    CompanyEntity mapToEntity(final Company company);
}

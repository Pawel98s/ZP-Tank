package pl.pawlo.zptank.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.company.CompanyEntity;
import pl.pawlo.zptank.database.repository.jpa.company.CompanyJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.CompanyEntityMapper;
import pl.pawlo.zptank.domain.company.Company;
import pl.pawlo.zptank.service.dao.CompanyDAO;

@Repository
@AllArgsConstructor
public class CompanyRepository implements CompanyDAO {

    private final CompanyJpaRepository companyJpaRepository;
    private final CompanyEntityMapper companyEntityMapper;


    @Override
    public Company save(Company company) {
        CompanyEntity save = companyJpaRepository.save(companyEntityMapper.mapToEntity(company));
        return companyEntityMapper.mapToDomain(save);
    }
}

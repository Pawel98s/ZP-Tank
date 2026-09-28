package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.company.Company;

import java.util.Optional;

public interface CompanyDAO {

    Company save(Company company);

    Optional<Company> findById(Long id);

    Company update(Company company);
}

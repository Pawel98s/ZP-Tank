package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.zptank.domain.company.Company;
import pl.pawlo.zptank.service.dao.CompanyDAO;

@Service
@AllArgsConstructor
public class CompanyService {

    CompanyDAO companyDAO;

    public Company save(Company company){
        return companyDAO.save(company);
    }
}

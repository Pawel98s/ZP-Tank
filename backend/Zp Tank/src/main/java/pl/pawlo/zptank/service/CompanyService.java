package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawlo.zptank.domain.company.Company;
import pl.pawlo.zptank.service.dao.CompanyDAO;

@Service
@AllArgsConstructor
public class CompanyService {

    CompanyDAO companyDAO;

    public Company save(Company company){
        return companyDAO.save(company);
    }

    public Company findById(Long id){
        return companyDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

    @Transactional
    public Company update(Long id, Company company) {
        Company existingCompany = findById(id);

        Company updateCompany = Company.builder()
                .id(existingCompany.getId())
                .name(company.getName() != null
                        ? company.getName()
                        : existingCompany.getName())
                .carrierLiabilityInsuranceValidUntil(company.getCarrierLiabilityInsuranceValidUntil() != null
                        ? company.getCarrierLiabilityInsuranceValidUntil()
                        : existingCompany.getCarrierLiabilityInsuranceValidUntil())
                .businessLiabilityInsuranceValidUntil(company.getBusinessLiabilityInsuranceValidUntil() != null
                        ? company.getBusinessLiabilityInsuranceValidUntil()
                        : existingCompany.getBusinessLiabilityInsuranceValidUntil())
                .cashRegisterInspectionDate(company.getCashRegisterInspectionDate() != null
                        ? company.getCashRegisterInspectionDate()
                        : existingCompany.getCashRegisterInspectionDate())
                .companyCardValidUntil(company.getCompanyCardValidUntil() != null
                        ? company.getCompanyCardValidUntil()
                        : existingCompany.getCompanyCardValidUntil())
                .ppkValidUntil(company.getPpkValidUntil() != null
                        ? company.getPpkValidUntil()
                        : existingCompany.getPpkValidUntil())
                .build();

        return companyDAO.update(updateCompany);
    }


}

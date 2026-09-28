package pl.pawlo.zptank.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.company.Company;
import pl.pawlo.zptank.service.dao.CompanyDAO;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyDAO companyDAO;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void shouldSaveCompany() {

        Company company = Company.builder()
                .id(1L)
                .name("CCO")
                .build();

        Mockito.when(companyDAO.save(company))
                .thenReturn(company);

        Company save = companyService.save(company);

        Assertions.assertThat(save).isEqualTo(company);
    }

    @Test
    void shouldFindCompanyById() {
        Company company = Company.builder()
                .id(1L)
                .name("CCO")
                .build();

        Mockito.when(companyDAO.findById(1L))
                .thenReturn(Optional.of(company));

        Company result = companyService.findById(1L);

        Assertions.assertThat(result).isEqualTo(company);
    }

    @Test
    void shouldUpdateCompany() {
        Company existingCompany = Company.builder()
                .id(1L)
                .name("Old Name")
                .build();

        Company updatedCompany = Company.builder()
                .id(1L)
                .name("New Name")
                .build();

        Mockito.when(companyDAO.findById(1L)).thenReturn(Optional.of(existingCompany));
        Mockito.when(companyDAO.update(updatedCompany)).thenReturn(updatedCompany);

        Company result = companyService.update(1L, updatedCompany);

        Assertions.assertThat(result.getName()).isEqualTo("New Name");
    }
}

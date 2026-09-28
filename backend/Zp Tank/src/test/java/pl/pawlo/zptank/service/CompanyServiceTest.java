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
}

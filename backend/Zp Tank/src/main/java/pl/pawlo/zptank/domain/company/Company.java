package pl.pawlo.zptank.domain.company;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Company {

    private Long id;
    private String name;
    private LocalDate carrierLiabilityInsuranceValidUntil;
    private LocalDate businessLiabilityInsuranceValidUntil;
    private LocalDate cashRegisterInspectionDate;
    private LocalDate companyCardValidUntil;
    private LocalDate ppkValidUntil;
}

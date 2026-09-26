package pl.pawlo.zptank.api.dto.company;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {

    private Long id;
    private String name;
    private LocalDate carrierLiabilityInsuranceValidUntil;
    private LocalDate businessLiabilityInsuranceValidUntil;
    private LocalDate cashRegisterInspectionDate;
    private LocalDate companyCardValidUntil;
    private LocalDate ppkValidUntil;
}

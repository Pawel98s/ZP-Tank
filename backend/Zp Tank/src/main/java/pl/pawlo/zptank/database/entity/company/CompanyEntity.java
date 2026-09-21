package pl.pawlo.zptank.database.entity.company;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "carrier_liability_insurance_valid_until")
    private LocalDate carrierLiabilityInsuranceValidUntil;

    @Column(name = "business_liability_insurance_valid_until")
    private LocalDate businessLiabilityInsuranceValidUntil;

    @Column(name = "cash_register_inspection_date")
    private LocalDate cashRegisterInspectionDate;

    @Column(name = "company_card_valid_until")
    private LocalDate companyCardValidUntil;

    @Column(name = "ppk_valid_until")
    private LocalDate ppkValidUntil;
}

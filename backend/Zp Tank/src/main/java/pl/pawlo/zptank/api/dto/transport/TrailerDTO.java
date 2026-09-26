package pl.pawlo.zptank.api.dto.transport;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrailerDTO {

    private Long id;
    private String registrationNumber;
    private BigDecimal capacity;
    private LocalDate insuranceValidUntil;
    private LocalDate periodicInspectionDate;
    private LocalDate intermediateInspectionDate;
    private LocalDate redStripeValidUntil;
    private LocalDate onLegalizationValidUntil;
    private LocalDate pbLegalizationValidUntil;

}

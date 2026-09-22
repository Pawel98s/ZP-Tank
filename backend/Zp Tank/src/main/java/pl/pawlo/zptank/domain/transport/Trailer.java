package pl.pawlo.zptank.domain.transport;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Trailer {

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

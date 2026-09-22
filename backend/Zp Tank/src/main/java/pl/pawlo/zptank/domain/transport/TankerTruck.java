package pl.pawlo.zptank.domain.transport;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class TankerTruck {


    private Long id;
    private String registrationNumber;
    private String brand;
    private String model;
    private BigDecimal capacity;
    private LocalDate insuranceValidUntil;
    private LocalDate periodicInspectionDate;
    private LocalDate intermediateInspectionDate;
    private LocalDate redStripeValidUntil;
    private LocalDate onLegalizationValidUntil;
    private LocalDate pbLegalizationValidUntil;
    private LocalDate tachographReadingDate;
    private LocalDate tachographCalibrationDate;
    private String gps;

}

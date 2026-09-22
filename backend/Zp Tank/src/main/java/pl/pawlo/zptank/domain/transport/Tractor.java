package pl.pawlo.zptank.domain.transport;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Tractor {

    private Long id;
    private String registrationNumber;
    private String brand;
    private String model;
    private LocalDate periodicInspectionDate;
    private LocalDate insuranceValidUntil;
    private LocalDate redStripeValidUntil;
    private LocalDate tachographReadingDate;
    private LocalDate tachographCalibrationDate;
    private String gps;

}

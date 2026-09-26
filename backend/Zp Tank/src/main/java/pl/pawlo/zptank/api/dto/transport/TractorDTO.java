package pl.pawlo.zptank.api.dto.transport;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TractorDTO {

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

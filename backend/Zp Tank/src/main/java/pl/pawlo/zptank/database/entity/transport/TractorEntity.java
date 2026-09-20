package pl.pawlo.zptank.database.entity.transport;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "tractorId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tractors")
public class TractorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "periodic_inspection_date")
    private LocalDate periodicInspectionDate;

    @Column(name = "insurance_valid_until")
    private LocalDate insuranceValidUntil;

    @Column(name = "red_stripe_valid_until")
    private LocalDate redStripeValidUntil;

    @Column(name = "tachograph_reading_date")
    private LocalDate tachographReadingDate;

    @Column(name = "tachograph_calibration_date")
    private LocalDate tachographCalibrationDate;

    @Column(name = "gps")
    private String gps;

}

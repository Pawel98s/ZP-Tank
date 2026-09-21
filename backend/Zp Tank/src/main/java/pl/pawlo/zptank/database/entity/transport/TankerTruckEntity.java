package pl.pawlo.zptank.database.entity.transport;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tanker_trucks")
public class TankerTruckEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "capacity", precision = 19, scale = 3)
    private BigDecimal capacity;

    @Column(name = "insurance_valid_until")
    private LocalDate insuranceValidUntil;

    @Column(name = "periodic_inspection_date")
    private LocalDate periodicInspectionDate;

    @Column(name = "intermediate_inspection_date")
    private LocalDate intermediateInspectionDate;

    @Column(name = "red_stripe_valid_until")
    private LocalDate redStripeValidUntil;

    @Column(name = "on_legalization_valid_until")
    private LocalDate onLegalizationValidUntil;

    @Column(name = "pb_legalization_valid_until")
    private LocalDate pbLegalizationValidUntil;

    @Column(name = "tachograph_reading_date")
    private LocalDate tachographReadingDate;

    @Column(name = "tachograph_calibration_date")
    private LocalDate tachographCalibrationDate;

    @Column(name = "gps")
    private String gps;

}

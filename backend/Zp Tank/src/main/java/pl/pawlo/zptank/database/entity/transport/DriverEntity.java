package pl.pawlo.zptank.database.entity.transport;

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
@Table(name = "drivers")
public class DriverEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_set_id", unique = true)
    private TransportSetEntity transportSet;

    @Column(name = "driver_card_valid_until")
    private LocalDate driverCardValidUntil;

    @Column(name = "adr_valid_until")
    private LocalDate adrValidUntil;

    @Column(name = "identity_document_valid_until")
    private LocalDate identityDocumentValidUntil;

    @Column(name = "psychological_examination_valid_until")
    private LocalDate psychologicalExaminationValidUntil;

    @Column(name = "tdt_valid_until")
    private LocalDate tdtValidUntil;

    @Column(name = "driving_license_valid_until")
    private LocalDate drivingLicenseValidUntil;

    @Column(name = "health_and_safety_training_valid_until")
    private LocalDate healthAndSafetyTrainingValidUntil;

    @Column(name = "medical_examination_valid_until")
    private LocalDate medicalExaminationValidUntil;

}

package pl.pawlo.zptank.domain.transport;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Driver {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String login;
    private String password;
    private boolean active;
    private TransportSet transportSet;
    private LocalDate driverCardValidUntil;
    private LocalDate adrValidUntil;
    private LocalDate identityDocumentValidUntil;
    private LocalDate psychologicalExaminationValidUntil;
    private LocalDate tdtValidUntil;
    private LocalDate drivingLicenseValidUntil;
    private LocalDate healthAndSafetyTrainingValidUntil;
    private LocalDate medicalExaminationValidUntil;

}

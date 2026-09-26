package pl.pawlo.zptank.api.dto.transport;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String login;
    private String password;
    private boolean active;
    private TransportSetDTO transportSet;
    private LocalDate driverCardValidUntil;
    private LocalDate adrValidUntil;
    private LocalDate identityDocumentValidUntil;
    private LocalDate psychologicalExaminationValidUntil;
    private LocalDate tdtValidUntil;
    private LocalDate drivingLicenseValidUntil;
    private LocalDate healthAndSafetyTrainingValidUntil;
    private LocalDate medicalExaminationValidUntil;

}

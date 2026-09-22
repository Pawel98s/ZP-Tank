package pl.pawlo.zptank.domain.employee;

import lombok.*;
import pl.pawlo.zptank.domain.EmployeeRole;

import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Employee {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private EmployeeRole role;
    private String login;
    private String password;
    private boolean workplaceInstruction;
    private boolean kseCertificate;
    private LocalDate medicalExaminationValidUntil;
    private LocalDate trainingValidUntil;
    private LocalDate healthAndSafetyValidUntil;

}

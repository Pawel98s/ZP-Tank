package pl.pawlo.zptank.api.dto.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import pl.pawlo.zptank.domain.EmployeeRole;

import java.time.LocalDate;

@Data
public class CreateEmployeeRequestDTO {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private EmployeeRole role;

    @NotBlank
    private String login;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    private Boolean workplaceInstruction;
    private Boolean kseCertificate;
    private LocalDate medicalExaminationValidUntil;
    private LocalDate trainingValidUntil;
    private LocalDate healthAndSafetyValidUntil;
}

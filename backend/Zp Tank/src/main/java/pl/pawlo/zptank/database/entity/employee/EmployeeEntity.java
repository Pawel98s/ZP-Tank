package pl.pawlo.zptank.database.entity.employee;

import jakarta.persistence.*;
import lombok.*;
import pl.pawlo.zptank.domain.EmployeeRole;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private EmployeeRole role;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "workplace_instruction", nullable = false)
    private boolean workplaceInstruction;

    @Column(name = "kse_certificate", nullable = false)
    private boolean kseCertificate;

    @Column(name = "medical_examination_valid_until")
    private LocalDate medicalExaminationValidUntil;

    @Column(name = "training_valid_until")
    private LocalDate trainingValidUntil;

    @Column(name = "health_and_safety_valid_until")
    private LocalDate healthAndSafetyValidUntil;

}

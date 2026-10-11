package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import pl.pawlo.zptank.api.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import pl.pawlo.zptank.domain.employee.Employee;
import pl.pawlo.zptank.service.dao.EmployeeDAO;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeDAO employeeDAO;

    public Employee save(Employee employee) {
        return employeeDAO.save(employee);
    }

    public Employee findById(Long id) {
        return employeeDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    public void deleteById(Long id) {
        Employee employee = findById(id);
        employeeDAO.deleteById(employee.getId());
    }

    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    public Employee update(Long id, Employee employee) {
        Employee existingEmployee = findById(id);

        Employee updatedEmployee = Employee.builder()
                .id(existingEmployee.getId())
                .firstName(employee.getFirstName() != null
                        ? employee.getFirstName()
                        : existingEmployee.getFirstName())
                .lastName(employee.getLastName() != null
                        ? employee.getLastName()
                        : existingEmployee.getLastName())
                .phone(employee.getPhone() != null
                        ? employee.getPhone()
                        : existingEmployee.getPhone())
                .email(employee.getEmail() != null
                        ? employee.getEmail()
                        : existingEmployee.getEmail())
                .role(employee.getRole() != null
                        ? employee.getRole()
                        : existingEmployee.getRole())
                .login(employee.getLogin() != null
                        ? employee.getLogin()
                        : existingEmployee.getLogin())
                .password(employee.getPassword() != null
                        ? employee.getPassword()
                        : existingEmployee.getPassword())
                .medicalExaminationValidUntil(employee.getMedicalExaminationValidUntil() != null
                        ? employee.getMedicalExaminationValidUntil()
                        : existingEmployee.getMedicalExaminationValidUntil())
                .trainingValidUntil(employee.getTrainingValidUntil() != null
                        ? employee.getTrainingValidUntil()
                        : existingEmployee.getTrainingValidUntil())
                .healthAndSafetyValidUntil(employee.getHealthAndSafetyValidUntil() != null
                        ? employee.getHealthAndSafetyValidUntil()
                        : existingEmployee.getHealthAndSafetyValidUntil())
                .workplaceInstruction(employee.getWorkplaceInstruction() != null
                        ? employee.getWorkplaceInstruction()
                        : existingEmployee.getWorkplaceInstruction())
                .kseCertificate(employee.getKseCertificate() != null
                        ? employee.getKseCertificate()
                        : existingEmployee.getKseCertificate())
                .build();

        return employeeDAO.save(updatedEmployee);

    }
}
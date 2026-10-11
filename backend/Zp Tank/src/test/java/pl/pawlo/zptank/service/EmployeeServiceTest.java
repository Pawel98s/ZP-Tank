package pl.pawlo.zptank.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.employee.Employee;
import pl.pawlo.zptank.service.dao.EmployeeDAO;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeDAO employeeDAO;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void shouldCreateEmployee() {

        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        Mockito.when(employeeDAO.save(employee)).thenReturn(employee);

        Employee save = employeeService.save(employee);

        Assertions.assertThat(save).isEqualTo(employee);
    }

    @Test
    void shouldFindEmployeeById() {

        Employee employee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        Mockito.when(employeeDAO.findById(1L)).thenReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.findById(1L);

        Assertions.assertThat(foundEmployee).isEqualTo(employee);
    }

    @Test
    void shouldFindAllEmployees() {

        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .build();

        Mockito.when(employeeDAO.findAll()).thenReturn(List.of(employee1, employee2));

        List<Employee> employees = employeeService.findAll();

        Assertions.assertThat(employees).containsExactlyInAnyOrder(employee1, employee2);
    }

    @Test
    void shouldDeleteEmployee() {
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        Mockito.doNothing().when(employeeDAO).deleteById(1L);
        Mockito.when(employeeDAO.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.deleteById(1L);

        Mockito.verify(employeeDAO).deleteById(1L);
    }

    @Test
    void shouldUpdateEmployee() {
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        Employee updatedEmployee = Employee.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Smith")
                .build();

        Mockito.when(employeeDAO.findById(1L)).thenReturn(Optional.of(employee));
        Mockito.when(employeeDAO.save(updatedEmployee)).thenReturn(updatedEmployee);

        Employee result = employeeService.update(1L, updatedEmployee);

        Assertions.assertThat(result).isEqualTo(updatedEmployee);
    }
}

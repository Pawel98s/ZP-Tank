package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.employee.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDAO {

    Employee save(Employee employee);

    Optional<Employee> findById(Long id);

    List<Employee> findAll();

    void deleteById(Long id);
}

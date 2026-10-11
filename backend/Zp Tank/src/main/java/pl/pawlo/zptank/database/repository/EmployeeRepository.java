package pl.pawlo.zptank.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.employee.EmployeeEntity;
import pl.pawlo.zptank.database.repository.jpa.employee.EmployeeJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.EmployeeEntityMapper;
import pl.pawlo.zptank.domain.employee.Employee;
import pl.pawlo.zptank.service.dao.EmployeeDAO;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class EmployeeRepository implements EmployeeDAO {

    private final EmployeeJpaRepository employeeJpaRepository;
    private final EmployeeEntityMapper employeeEntityMapper;


    @Override
    public Employee save(Employee employee) {
        EmployeeEntity save = employeeJpaRepository.save(employeeEntityMapper.mapToEntity(employee));
        return employeeEntityMapper.mapToDomain(save);
    }

    @Override
    public Optional<Employee> findById(Long id) {
       return employeeJpaRepository.findById(id)
                .map(employeeEntityMapper::mapToDomain);
    }

    @Override
    public List<Employee> findAll() {
        return employeeJpaRepository.findAll()
                .stream()
                .map(employeeEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        employeeJpaRepository.deleteById(id);
    }
}

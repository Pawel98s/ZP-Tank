package pl.pawlo.zptank.api.controller;

import lombok.AllArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.employee.EmployeeDTO;
import pl.pawlo.zptank.api.dto.employee.CreateEmployeeRequestDTO;
import pl.pawlo.zptank.api.mapper.EmployeeMapper;
import pl.pawlo.zptank.domain.employee.Employee;
import pl.pawlo.zptank.service.EmployeeService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EmployeeDTO save(
            @Valid @RequestBody CreateEmployeeRequestDTO employeeDTO) {
        Employee save = employeeService.save(employeeMapper.mapToDomain(employeeDTO));
        return employeeMapper.mapToDTO(save);
    }

    @GetMapping("/{id}")
    public EmployeeDTO findById(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        return employeeMapper.mapToDTO(employee);
    }

    @GetMapping
    public List<EmployeeDTO> findAll() {
        List<Employee> employees = employeeService.findAll();
        return employees.stream()
                .map(employeeMapper::mapToDTO)
                .toList();
    }

    @PatchMapping("/{id}")
    public EmployeeDTO update(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.mapToDomain(employeeDTO);
        Employee update = employeeService.update(id, employee);
        return employeeMapper.mapToDTO(update);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        employeeService.deleteById(id);
    }
}

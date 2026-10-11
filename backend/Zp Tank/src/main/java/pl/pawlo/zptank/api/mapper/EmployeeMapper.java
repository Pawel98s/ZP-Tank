package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.pawlo.zptank.api.dto.employee.CreateEmployeeRequestDTO;
import pl.pawlo.zptank.api.dto.employee.EmployeeDTO;
import pl.pawlo.zptank.domain.employee.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "password", ignore = true)
    Employee mapToDomain(final EmployeeDTO employeeDTO);

    @Mapping(target = "id", ignore = true)
    Employee mapToDomain(final CreateEmployeeRequestDTO employeeDTO);

    EmployeeDTO mapToDTO(final Employee employee);
}

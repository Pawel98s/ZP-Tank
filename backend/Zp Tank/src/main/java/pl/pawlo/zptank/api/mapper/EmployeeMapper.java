package pl.pawlo.zptank.api.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.zptank.api.dto.employee.EmployeeDTO;
import pl.pawlo.zptank.domain.employee.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee mapToDomain(final EmployeeDTO employeeDTO);

    EmployeeDTO mapToDTO(final Employee employee);
}

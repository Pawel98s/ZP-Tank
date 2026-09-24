package pl.pawlo.zptank.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.employee.EmployeeEntity;
import pl.pawlo.zptank.domain.employee.Employee;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeEntityMapper {

    Employee mapToDomain(final EmployeeEntity employeeEntity);

    EmployeeEntity mapToEntity(final Employee employee);
}

package pl.pawlo.zptank.database.repository.jpa.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.employee.EmployeeEntity;

import java.util.Optional;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<EmployeeEntity,Long> {

    Optional<EmployeeEntity> findByLogin(String login);

}

package pl.pawlo.zptank.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.pawlo.zptank.database.entity.employee.EmployeeEntity;
import pl.pawlo.zptank.database.repository.jpa.employee.EmployeeJpaRepository;

@Service
@RequiredArgsConstructor
public class EmployeeUserDetailsService implements UserDetailsService {

    private final EmployeeJpaRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        EmployeeEntity employee = employeeRepository.findByLogin(login)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(employee.getLogin())
                .password(employee.getPassword())
                .roles(employee.getRole().name())
                .build();
    }
}

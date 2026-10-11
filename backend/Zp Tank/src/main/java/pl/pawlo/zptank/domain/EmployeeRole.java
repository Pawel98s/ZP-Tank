package pl.pawlo.zptank.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmployeeRole {
    ADMIN("Admin"),
    USER("User"),
    DRIVER("Driver"),
    MECHANIC("Mechanic");

    final String label;
    }

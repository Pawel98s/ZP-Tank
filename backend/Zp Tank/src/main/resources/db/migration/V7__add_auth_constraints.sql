ALTER TABLE employees
    ALTER COLUMN login SET NOT NULL;

ALTER TABLE employees
    ALTER COLUMN password SET NOT NULL;

CREATE UNIQUE INDEX ux_employees_login
    ON employees (login);
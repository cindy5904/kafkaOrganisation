package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.entity.Employee;
import java.util.List;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {
    public List<Employee> findByOrganisationId(Long organisationId) {
        return find("organisationId", organisationId).list();
    }

    public List<Employee> findByDepartmentId(Long departmentId) {
        return find("departmentId", departmentId).list();
    }

    public List<Employee> findEmployeesWithoutDepartment() {
        return find("isDepartmentless", true).list();
    }

    public List<Employee> findEmployeesWithoutOrganisation() {
        return find("isOrganisationless", true).list();
    }
}

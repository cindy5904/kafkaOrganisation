package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.client.DepartementClient;
import org.example.client.OrganisationClient;
import org.example.dto.DepartementDto;
import org.example.dto.OrganisationDto;
import org.example.entity.Employee;
import org.example.kafka.EmployeeEventProducer;
import org.example.repository.EmployeeRepository;
import java.util.List;

@ApplicationScoped
public class EmployeeService {
    @Inject
    EmployeeRepository employeeRepository;
    @Inject
    EmployeeEventProducer employeeEventProducer;
    @Inject
    @RestClient
    OrganisationClient organisationClient;
    @Inject
    @RestClient
    DepartementClient departementClient;

    public OrganisationDto getOrganisation(Long organisationId) {
        return organisationClient.getOrganisationById(organisationId);
    }

    public List<Employee> getEmployeesByOrganisation(Long organisationId) {
        return employeeRepository.findByOrganisationId(organisationId);
    }

    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    public List<Employee> getEmployeesWithoutDepartment() {
        return employeeRepository.findEmployeesWithoutDepartment();
    }

    public List<Employee> getEmployeesWithoutOrganisation() {
        return employeeRepository.findEmployeesWithoutOrganisation();
    }


    @Transactional
    public void updateEmployeeOrganisationStatus(Long organisationId, boolean isOrganisationless) {
        List<Employee> employees = getEmployeesByOrganisation(organisationId);
        for (Employee employee : employees) {
            employee.setOrganisationless(isOrganisationless);
            if (isOrganisationless) {
                employee.setOrganisationId(null);
            }
            employeeRepository.persist(employee);
        }
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        OrganisationDto organisationDto = organisationClient.getOrganisationById(employee.getOrganisationId());
        if (organisationDto == null) {
            throw new IllegalArgumentException("Organisation non trouvée");
        }

        if (employee.getDepartmentId() != null) {
            DepartementDto departementDto = departementClient.getDepartementById(employee.getDepartmentId());
            if (departementDto == null) {
                employee.setDepartmentless(true);
            }
        } else {
            employee.setDepartmentless(true);
        }

        employeeRepository.persist(employee);

        employeeEventProducer.publishEmployeeAdded(employee.getOrganisationId());

        return employee;
    }

    @Transactional
    public boolean deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id);
        if (employee != null) {
            employeeEventProducer.publishEmployeeRemoved(employee.getOrganisationId());
            employeeRepository.delete(employee);
            return true;
        }
        return false;
    }


    @Transactional
    public void updateEmployeeDepartmentStatus(Long departmentId, boolean isDepartmentless) {
        List<Employee> employees = getEmployeesByDepartment(departmentId);
        for (Employee employee : employees) {
            employee.setDepartmentless(isDepartmentless);
            if (isDepartmentless) {
                employee.setDepartmentId(null);
            }
            employeeRepository.persist(employee);
        }
    }
}

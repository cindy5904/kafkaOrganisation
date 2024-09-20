package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

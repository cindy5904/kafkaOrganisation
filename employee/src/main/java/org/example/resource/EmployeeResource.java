package org.example.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entity.Employee;
import org.example.service.EmployeeService;

import java.util.List;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {
    @Inject
    EmployeeService employeeService;

    @GET
    public List<Employee> getAllEmployees() {
        return employeeService.getEmployeesWithoutDepartment();
    }

    @GET
    @Path("/by-organisation/{organisationId}")
    public List<Employee> getEmployeesByOrganisation(@PathParam("organisationId") Long organisationId) {
        return employeeService.getEmployeesByOrganisation(organisationId);
    }

    @GET
    @Path("/by-department/{departmentId}")
    public List<Employee> getEmployeesByDepartment(@PathParam("departmentId") Long departmentId) {
        return employeeService.getEmployeesByDepartment(departmentId);
    }

    @GET
    @Path("/without-department")
    public List<Employee> getEmployeesWithoutDepartment() {
        return employeeService.getEmployeesWithoutDepartment();
    }

    @GET
    @Path("/without-organisation")
    public List<Employee> getEmployeesWithoutOrganisation() {
        return employeeService.getEmployeesWithoutOrganisation();
    }

    @POST
    @Transactional
    public Response createEmployee(Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return Response.status(Response.Status.CREATED).entity(createdEmployee).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteEmployee(@PathParam("id") Long id) {
        boolean deleted = employeeService.deleteEmployee(id);
        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/update-organisation-status/{organisationId}")
    @Transactional
    public Response updateOrganisationStatus(@PathParam("organisationId") Long organisationId, boolean isOrganisationless) {
        employeeService.updateEmployeeOrganisationStatus(organisationId, isOrganisationless);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/update-department-status/{departmentId}")
    @Transactional
    public Response updateDepartmentStatus(@PathParam("departmentId") Long departmentId, boolean isDepartmentless) {
        employeeService.updateEmployeeDepartmentStatus(departmentId, isDepartmentless);
        return Response.status(Response.Status.OK).build();
    }

}

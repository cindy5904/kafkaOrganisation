package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.entity.Departement;
import org.example.rrepository.DepartementRepository;

import java.util.List;

@ApplicationScoped
public class DepartementService {
    @Inject
    DepartementRepository departementRepository;

    public List<Departement> getAllDepartments() {
        return departementRepository.listAll();
    }

    public List<Departement> getDepartmentsByOrganisation(Long organisationId) {
        return departementRepository.findByOrganisationId(organisationId);
    }

    public List<Departement> getOrganisationlessDepartments() {
        return departementRepository.findOrganisationlessDepartments();
    }

    @Transactional
    public Departement createDepartment(Departement departement) {
        departementRepository.persist(departement);
        return departement;
    }
    @Transactional
    public boolean deleteDepartment(Long id) {
        Departement departement = departementRepository.findById(id);
        if (departement != null) {
            departementRepository.delete(departement);
            return true;
        }
        return false;
    }
    @Transactional
    public void handleOrganisationDeletion(Long organisationId) {
        List<Departement> departments = getDepartmentsByOrganisation(organisationId);
        for (Departement department : departments) {
            department.setOrganisationless(true);
            department.setOrganisationId(null);
            departementRepository.persist(department);
        }
    }
}

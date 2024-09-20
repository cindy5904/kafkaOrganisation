package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.entity.Organisation;
import org.example.repository.OrganisationRepository;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class OrganisationService {
    @Inject
    OrganisationRepository organisationRepository;


    public List<Organisation> getAllOrganisations() {
        return organisationRepository.listAll();
    }


    public Organisation getOrganisationById(Long id) {
        return organisationRepository.findById(id);
    }


    @Transactional
    public Organisation createOrganisation(Organisation organisation) {
        organisation.setLastUpdateDate(LocalDate.now());
        organisationRepository.persist(organisation);
        return organisation;
    }


    @Transactional
    public boolean deleteOrganisation(Long id) {
        Organisation organisation = organisationRepository.findById(id);
        if (organisation != null) {
            organisationRepository.delete(organisation);
            return true;
        }
        return false;
    }


    @Transactional
    public void updateEmployeeCount(Long organisationId, int newEmployeeCount) {
        Organisation organisation = organisationRepository.findById(organisationId);
        if (organisation != null) {
            organisation.setNumberOfEmployees(newEmployeeCount);
            organisation.setLastUpdateDate(LocalDate.now());
            organisationRepository.persist(organisation);
        }
    }


    @Transactional
    public Organisation updateOrganisation(Long id, Organisation updatedOrganisation) {
        Organisation organisation = organisationRepository.findById(id);
        if (organisation != null) {
            organisation.setName(updatedOrganisation.getName());
            organisation.setAddress(updatedOrganisation.getAddress());
            organisation.setLastUpdateDate(LocalDate.now());
            organisationRepository.persist(organisation);
            return organisation;
        }
        return null;
    }
    @Transactional
    public void incrementEmployeeCount(Long organisationId) {
        Organisation organisation = organisationRepository.findById(organisationId);
        if (organisation != null) {
            organisation.setNumberOfEmployees(organisation.getNumberOfEmployees() + 1);
            organisation.setLastUpdateDate(LocalDate.now());
            organisationRepository.persist(organisation);
        }
    }

    @Transactional
    public void decrementEmployeeCount(Long organisationId) {
        Organisation organisation = organisationRepository.findById(organisationId);
        if (organisation != null) {
            organisation.setNumberOfEmployees(organisation.getNumberOfEmployees() - 1);
            organisation.setLastUpdateDate(LocalDate.now());
            organisationRepository.persist(organisation);
        }
    }
}

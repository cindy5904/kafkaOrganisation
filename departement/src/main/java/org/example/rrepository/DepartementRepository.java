package org.example.rrepository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.entity.Departement;

import java.util.List;

@ApplicationScoped
public class DepartementRepository implements PanacheRepository<Departement> {
    public List<Departement> findByOrganisationId(Long organisationId) {
        return find("organisationId", organisationId).list();
    }

    public List<Departement> findOrganisationlessDepartments() {
        return find("isOrganisationless", true).list();
    }
}

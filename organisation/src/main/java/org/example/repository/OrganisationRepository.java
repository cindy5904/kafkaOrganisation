package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.entity.Organisation;

@ApplicationScoped
public class OrganisationRepository implements PanacheRepository<Organisation> {
}

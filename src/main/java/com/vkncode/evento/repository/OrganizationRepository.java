package com.vkncode.evento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vkncode.evento.model.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long>{

}

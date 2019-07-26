package com.vkncode.evento.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vkncode.evento.model.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
	List<Guest> findByOrganizationId(Long organizationId);
}

package com.vkncode.evento.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vkncode.evento.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
	List<Event> findByOrganizationId(Long organizationId);
}

package com.vkncode.evento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vkncode.evento.model.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
	List<Attendance> findByEventId(Long eventId);
	List<Attendance> findByGuestId(Long guestId);
}

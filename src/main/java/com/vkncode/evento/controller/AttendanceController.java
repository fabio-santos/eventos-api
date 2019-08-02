package com.vkncode.evento.controller;

import com.vkncode.evento.exception.ResourceNotFoundException;
import com.vkncode.evento.model.Attendance;
import com.vkncode.evento.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/attendance")
@PreAuthorize("hasRole('USER')")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @GetMapping
    public List<Attendance> getAttendances() {
        return attendanceRepository.findAll();
    }
    
    @GetMapping("/event/{eventId}")
    public List<Attendance> getAttendancesByOrganization(@PathVariable Long eventId) {
        return attendanceRepository.findByEventId(eventId);
    }
    
    @GetMapping("/guest/{guestId}")
    public List<Attendance> getAttendancesByGuest(@PathVariable Long guestId) {
        return attendanceRepository.findByGuestId(guestId);
    }
    
    @GetMapping("/{attendanceId}")
    public Attendance getSingleAttendance(@PathVariable Long attendanceId) {
        return attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance", "ID", attendanceId));
    }

    @PostMapping
    public Attendance createAttendance(@Valid @RequestBody Attendance Attendance) {
        return attendanceRepository.save(Attendance);
    }

    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<?> deleteAttendance(@PathVariable Long attendanceId) {
        return attendanceRepository.findById(attendanceId)
                .map(Attendance -> {
                	attendanceRepository.delete(Attendance);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Attendance", "ID", attendanceId));
    }
}
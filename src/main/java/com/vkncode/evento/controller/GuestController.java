package com.vkncode.evento.controller;

import com.vkncode.evento.exception.ResourceNotFoundException;
import com.vkncode.evento.model.Guest;
import com.vkncode.evento.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/guests")
@PreAuthorize("hasRole('USER')")
public class GuestController {

    @Autowired
    private GuestRepository guestRepository;

    @GetMapping
    public List<Guest> getGuests() {
        return guestRepository.findAll();
    }
    
    @GetMapping("/organization/{organizationId}")
    public List<Guest> getGuestsByOrganization(@PathVariable Long organizationId) {
        return guestRepository.findByOrganizationId(organizationId);
    }
    
    @GetMapping("/{guestId}")
    public Guest getSingleGuest(@PathVariable Long guestId) {
        return guestRepository.findById(guestId)
                .orElseThrow(() -> new ResourceNotFoundException("Guest", "ID", guestId));
    }

    @PostMapping
    public Guest createGuest(@Valid @RequestBody Guest Guest) {
        return guestRepository.save(Guest);
    }

    @PutMapping("/{guestId}")
    public Guest updateGuest(@PathVariable Long guestId,
                                   @Valid @RequestBody Guest GuestRequest) {
        return guestRepository.findById(guestId)
                .map(Guest -> {
                	Guest.setEmail(GuestRequest.getEmail());
                	Guest.setPhone(GuestRequest.getPhone());
                	Guest.setName(GuestRequest.getName());
                	Guest.setBirth(GuestRequest.getBirth());
                	Guest.setOrganization(GuestRequest.getOrganization());
                    return guestRepository.save(Guest);
                }).orElseThrow(() -> new ResourceNotFoundException("Guest", "ID", guestId));
    }

    @DeleteMapping("/{guestId}")
    public ResponseEntity<?> deleteGuest(@PathVariable Long guestId) {
        return guestRepository.findById(guestId)
                .map(Guest -> {
                	guestRepository.delete(Guest);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Guest", "ID", guestId));
    }
}
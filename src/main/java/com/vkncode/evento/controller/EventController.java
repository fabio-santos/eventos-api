package com.vkncode.evento.controller;

import com.vkncode.evento.exception.ResourceNotFoundException;
import com.vkncode.evento.model.Event;
import com.vkncode.evento.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/events")
@PreAuthorize("hasRole('USER')")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }
    
    @GetMapping("/organization/{organizationId}")
    public List<Event> getEventsByOrganization(@PathVariable Long organizationId) {
        return eventRepository.findByOrganizationId(organizationId);
    }
    
    @GetMapping("/{eventId}")
    public Event getSingleEvent(@PathVariable Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "ID", eventId));
    }

    @PostMapping
    public Event createEvent(@Valid @RequestBody Event Event) {
        return eventRepository.save(Event);
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(@PathVariable Long eventId,
                                   @Valid @RequestBody Event EventRequest) {
        return eventRepository.findById(eventId)
                .map(Event -> {
                	Event.setName(EventRequest.getName());
                	Event.setPeopleLimit(EventRequest.getPeopleLimit());
                	Event.setDate(EventRequest.getDate());
                	Event.setOrganization(EventRequest.getOrganization());
                    return eventRepository.save(Event);
                }).orElseThrow(() -> new ResourceNotFoundException("Event", "ID", eventId));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId) {
        return eventRepository.findById(eventId)
                .map(Event -> {
                	eventRepository.delete(Event);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Event", "ID", eventId));
    }
}
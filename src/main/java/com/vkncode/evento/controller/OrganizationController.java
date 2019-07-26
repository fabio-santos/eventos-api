package com.vkncode.evento.controller;

import com.vkncode.evento.exception.ResourceNotFoundException;
import com.vkncode.evento.model.Organization;
import com.vkncode.evento.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/organizations")
@PreAuthorize("hasRole('USER')")
public class OrganizationController {

    @Autowired
    private OrganizationRepository organizationRepository;

    @GetMapping
    public List<Organization> getOrganizations() {
        return organizationRepository.findAll();
    }
    
    @GetMapping("/{organizationId}")
    public Organization getSingleOrganization(@PathVariable Long organizationId) {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", "ID", organizationId));
    }

    @PostMapping
    public Organization createOrganization(@Valid @RequestBody Organization organization) {
        return organizationRepository.save(organization);
    }

    @PutMapping("/{organizationId}")
    public Organization updateOrganization(@PathVariable Long organizationId,
                                   @Valid @RequestBody Organization organizationRequest) {
        return organizationRepository.findById(organizationId)
                .map(organization -> {
                	organization.setDocument(organizationRequest.getDocument());
                	organization.setName(organizationRequest.getName());
                	organization.setAddress(organizationRequest.getAddress());
                    return organizationRepository.save(organization);
                }).orElseThrow(() -> new ResourceNotFoundException("Organization", "ID", organizationId));
    }

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<?> deleteOrganization(@PathVariable Long organizationId) {
        return organizationRepository.findById(organizationId)
                .map(organization -> {
                	organizationRepository.delete(organization);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Organization", "ID", organizationId));
    }
}
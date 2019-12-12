package com.vkncode.evento.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vkncode.evento.exception.ResourceNotFoundException;
import com.vkncode.evento.model.Organization;
import com.vkncode.evento.repository.OrganizationRepository;

@RestController
@RequestMapping("/api/organizations")
@PreAuthorize("hasRole('USER')")
public class OrganizationController {

    @Autowired
    private OrganizationRepository organizationRepository;

    @GetMapping
    @Cacheable(value = "organizationList")
    public Page<Organization> getOrganizations(@PageableDefault(sort = "id", direction = Direction.ASC, size = 2) Pageable pageable) {
    	return organizationRepository.findAll(pageable);
    }
    
    @GetMapping("/{organizationId}")
    public Organization getSingleOrganization(@PathVariable Long organizationId) {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", "ID", organizationId));
    }

    @PostMapping
    @CacheEvict(value = "organizationList", allEntries = true)
    public Organization createOrganization(@Valid @RequestBody Organization organization) {
        return organizationRepository.save(organization);
    }

    @PutMapping("/{organizationId}")
    @CacheEvict(value = "organizationList", allEntries = true)
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
    @CacheEvict(value = "organizationList", allEntries = true)
    public ResponseEntity<?> deleteOrganization(@PathVariable Long organizationId) {
        return organizationRepository.findById(organizationId)
                .map(organization -> {
                	organizationRepository.delete(organization);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Organization", "ID", organizationId));
    }
}
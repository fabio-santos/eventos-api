package com.vkncode.evento.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import com.vkncode.evento.model.audit.DateAudit;

@Entity
@Table(name = "events")
public class Event extends DateAudit{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@NotBlank
    @Size(max = 200)
    private String name;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

	private Integer peopleLimit;
    
    private LocalDateTime date;
    
    public Event() {

	}
    
    public Event(Long id) {
		this.id = id;
	}
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Integer getPeopleLimit() {
		return peopleLimit;
	}

	public void setPeopleLimit(Integer peopleLimit) {
		this.peopleLimit = peopleLimit;
	}
}

package com.vkncode.evento.model;

import com.vkncode.evento.model.audit.UserDateAudit;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "guests", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "phone"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})

public class Guest extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    @NotBlank
    @Size(max = 50)
    private String phone;
    
    @NotBlank
    @Size(max = 200)
    private String name;

    private LocalDate birth;
    
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;
    
	public Guest() {
		
	}
	
    public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
}
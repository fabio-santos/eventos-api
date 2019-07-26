package com.vkncode.evento.model;

import com.vkncode.evento.model.audit.UserDateAudit;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "organizations", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "document"
        })
})

public class Organization extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NaturalId
    @Size(max = 50)
    private String document;
    
    @NotBlank
    @Size(max = 200)
    private String name;
    
    @Size(max = 300)
    private String address;
    
    public Organization() {
    	
    }
    
    public Organization(Long id) {
    	this.id = id;
    }

	public Long getId() {
		return id;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
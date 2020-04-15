package com.goncha.umbrella.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Patient implements Serializable{

	private static final long serialVersionUID = 5185980267516677886L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long id;
	
	@Column
	private Boolean enabled;

	@Column 
	private String firstName;
	
	@Column 
	private String lastName;

}

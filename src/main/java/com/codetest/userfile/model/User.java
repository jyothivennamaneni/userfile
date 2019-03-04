package com.codetest.userfile.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="first_name")
	@NotNull
	@Size(min=2, max=20)
	private String firstName;
	
	@Column(name="last_name")
	@NotNull
	@Size(min=2, max=20)
	private String lastName;
	
	@Column(name="email")
	@Email
	@Size(min=5)
	private String email;
	
	@Column(name="phone_number")
	@NotNull
	@Size(min=10, max=15)
private String phoneNumber;
	

	
	
	public User() {}
public User(Long id, String firstName, String lastName, String email, String phoneNumber) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		
	}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getfirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhoneNumber() {
	return phoneNumber;
}
public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}

@Override
public String toString() {
	return "User [id=" + id + ", FirstName=" + firstName + ", LastName=" + lastName + ", email=" + email
			+ ", phoneNumber=" + phoneNumber + "]";
}


	
	}



package com.sebarber.footballteams.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person {
	
	@Id
	private String email;
	private String firstName;
	private String lastName;	
	
	public Person(String email, String firstName, String lastName) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Person() {
		super();
	}
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return  firstName + " " + lastName + " (" + email + ")";
	}
	
	
	

}

package com.sebarber.footballteams.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Competition {
	
	@Id 
	private String name;
	private String location;
	private Date date;	
	
	
	public Competition(String name, String location, Date date) {
		super();
		this.name = name;
		this.location = location;
		this.date = date;
	}

	public Competition() {
		super();
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public Date getDate() {
		return date;
	}

}

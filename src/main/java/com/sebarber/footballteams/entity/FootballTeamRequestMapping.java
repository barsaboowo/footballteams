package com.sebarber.footballteams.entity;

import java.util.Set;

public class FootballTeamRequestMapping {
	
	private String name;
	private String ownerEmail;
	private String competition;
	private String city;
	private Set<String> teamEmails;
	
	public FootballTeamRequestMapping() {
		super();
	}

	public FootballTeamRequestMapping(String name, String ownerEmail, String competition, String city,
			Set<String> teamEmails) {
		super();
		this.name = name;
		this.ownerEmail = ownerEmail;
		this.competition = competition;
		this.city = city;
		this.teamEmails = teamEmails;
	}

	public String getName() {
		return name;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public String getCompetition() {
		return competition;
	}

	public String getCity() {
		return city;
	}

	public Set<String> getTeamEmails() {
		return teamEmails;
	}
	
	
	
	
	
	

}

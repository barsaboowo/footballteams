package com.sebarber.footballteams.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class FootballTeam {
	
	@Id
	private String name;
	@OneToOne(cascade=CascadeType.ALL)
	private Person owner;
	private String city;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Competition competition;
	
	@ManyToMany(cascade=CascadeType.ALL)	
	private Set<Person> players;
	private Date created;
	
	
	public FootballTeam(String name, Person owner, String city, Competition competition, Set<Person> players, Date created) {
		super();
		this.name = name;
		this.owner = owner;
		this.city = city;
		this.competition = competition;
		this.players = players;
		this.created = created;
	}

	public FootballTeam() {
		super();
	}

	public String getName() {
		return name;
	}

	public Person getOwner() {
		return owner;
	}

	public Competition getCompetition() {
		return competition;
	}

	public Set<Person> getPlayers() {
		return players;
	}

	public Date getCreated() {
		return created;
	}

	public String getCity() {
		return city;
	}
	
	
	
	

}

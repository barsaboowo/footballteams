package com.sebarber.footballteams.service;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.sebarber.footballteams.dao.FootballTeamDAO;
import com.sebarber.footballteams.entity.Competition;
import com.sebarber.footballteams.entity.FootballTeam;
import com.sebarber.footballteams.entity.FootballTeamRequestMapping;
import com.sebarber.footballteams.entity.Person;
import com.sebarber.footballteams.service.exceptions.PastCompetitionException;

public class FootballTeamServiceImpl implements FootballTeamService {
	
	private static final Logger LOG = Logger.getLogger(FootballTeamServiceImpl.class);

	@Autowired
	private FootballTeamDAO teamDAO;

	@Autowired
	private CompetitionService competitionService;

	@Autowired
	private PersonService personService;

	public FootballTeamServiceImpl() {
		super();
	}

	public FootballTeamServiceImpl(FootballTeamDAO teamDAO, CompetitionService competitionService,
			PersonService personService) {
		super();
		this.teamDAO = teamDAO;
		this.competitionService = competitionService;
		this.personService = personService;
	}

	@Override
	public Collection<FootballTeam> findAll() {
		LOG.info("Finding all teams");
		return teamDAO.findAll();
	}

	@Override
	public FootballTeam get(String key) {
		LOG.info("finding team with name " + key);
		return teamDAO.findOne(key);
	}

	@Override
	public void save(String key, FootballTeam entity) {
		LOG.info("Saving team with key " + key);
		teamDAO.saveAndFlush(entity);
	}

	@Override
	public void saveFootballTeam(FootballTeamRequestMapping team) throws EntityNotFoundException, EntityExistsException, PastCompetitionException {
		LOG.info("Saving team with name " + team.getName());
		
		if(teamDAO.exists(team.getName())){
			String message = "Team with name " + team.getName() + " already exists";
			LOG.warn(message);
			throw new EntityExistsException(message);
		}
		
		Competition competition = competitionService.findFutureCompetitionByName(team.getCompetition());
		Person owner = personService.get(team.getOwnerEmail());
		
		Set<Person> players = team.getTeamEmails().stream().map(email->personService.get(email)).collect(Collectors.toSet());
		
		FootballTeam footballTeam = new FootballTeam(team.getName(), owner, team.getCity(), competition, players, new Date());
		
		teamDAO.saveAndFlush(footballTeam);
		
		LOG.info("Team saved successfully");

	}

}

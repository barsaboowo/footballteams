package com.sebarber.footballteams;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.sebarber.footballteams.dao.FootballTeamDAO;
import com.sebarber.footballteams.entity.Competition;
import com.sebarber.footballteams.entity.FootballTeamRequestMapping;
import com.sebarber.footballteams.entity.Person;
import com.sebarber.footballteams.service.CompetitionService;
import com.sebarber.footballteams.service.FootballTeamService;
import com.sebarber.footballteams.service.FootballTeamServiceImpl;
import com.sebarber.footballteams.service.PersonService;
import com.sebarber.footballteams.service.exceptions.PastCompetitionException;

import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public class FootballTeamServiceTest {	
	private static final String TEAM_ASTON_VILLA = "Aston Villa";
	private static final String TEAM_ALREADY_EXISTS = "TEAM_ALREADY_EXISTS";
	
	private static final Person OWNER_LAMBERT = new Person("paul.lambert@astonvilla.com", "Paul", "Lambert");
	private static final Person PLAYER_HENRY = new Person("thierry.henry@arsenal.com", "Thierry", "Henry");
	private static final Person PLAYER_BENTEKE = new Person("christian.bentek@astonvilla.com", "Christian", "Benteke");
	private static final String PLAYER_UNKNOWN = "PLAYER_UNKNOWN";
	
	private static final Competition COMPETITION = new Competition("FA Cup", "Wembley", new Date());
	private static final String COMPETITION_UNKNOWN = "COMPETITION_UNKNOWN";
	private static final String COMPETITION_PAST = "COMPETITION_PAST";
	
	@Mock
	private FootballTeamDAO footballTeamDAO;
	
	@Mock
	private PersonService personService;
	
	@Mock
	private CompetitionService competitionService;
	
	private FootballTeamService footballTeamService;

	@Before
	public void before() throws EntityNotFoundException, PastCompetitionException{
		footballTeamDAO = Mockito.mock(FootballTeamDAO.class);
		personService = Mockito.mock(PersonService.class);
		competitionService = Mockito.mock(CompetitionService.class);
		
		when(personService.get(PLAYER_BENTEKE.getEmail())).thenReturn(PLAYER_BENTEKE);
		when(personService.get(PLAYER_HENRY.getEmail())).thenReturn(PLAYER_HENRY);
		when(personService.get(OWNER_LAMBERT.getEmail())).thenReturn(OWNER_LAMBERT);
		when(personService.get(PLAYER_UNKNOWN)).thenThrow(EntityNotFoundException.class);
		
		when(competitionService.findFutureCompetitionByName(COMPETITION.getName())).thenReturn(COMPETITION);
		when(competitionService.findFutureCompetitionByName(COMPETITION_UNKNOWN)).thenThrow(EntityNotFoundException.class);
		when(competitionService.findFutureCompetitionByName(COMPETITION_PAST)).thenThrow(PastCompetitionException.class);
		
		when(footballTeamDAO.exists(TEAM_ALREADY_EXISTS)).thenReturn(true);
		
		footballTeamService = new FootballTeamServiceImpl(footballTeamDAO, competitionService, personService);
		
	}
	
	@Test
	public void testFootballService() throws EntityNotFoundException, EntityExistsException, PastCompetitionException{		
		Set<String> playerEmails = new HashSet<>();
		playerEmails.add(PLAYER_BENTEKE.getEmail());
		playerEmails.add(PLAYER_HENRY.getEmail());
		
		FootballTeamRequestMapping mapping = 
				new FootballTeamRequestMapping(TEAM_ASTON_VILLA, OWNER_LAMBERT.getEmail(), COMPETITION.getName(), "city", playerEmails);
		
		footballTeamService.saveFootballTeam(mapping);
	}
	
	@Test(expected=EntityExistsException.class)
	public void testExceptionThrownWhenTeamExists() throws EntityNotFoundException, EntityExistsException, PastCompetitionException{
		Set<String> playerEmails = new HashSet<>();
		playerEmails.add(PLAYER_BENTEKE.getEmail());
		playerEmails.add(PLAYER_HENRY.getEmail());
		
		FootballTeamRequestMapping mapping = 
				new FootballTeamRequestMapping(TEAM_ALREADY_EXISTS, OWNER_LAMBERT.getEmail(), COMPETITION.getName(), "city", playerEmails);
		
		footballTeamService.saveFootballTeam(mapping);
	}
	
	@Test(expected=EntityNotFoundException.class)
	public void testExceptionThrownWhenOwnerDoesNotExist() throws EntityNotFoundException, EntityExistsException, PastCompetitionException{
		Set<String> playerEmails = new HashSet<>();
		playerEmails.add(PLAYER_BENTEKE.getEmail());
		playerEmails.add(PLAYER_HENRY.getEmail());
		
		FootballTeamRequestMapping mapping = 
				new FootballTeamRequestMapping(TEAM_ASTON_VILLA, PLAYER_UNKNOWN, COMPETITION.getName(), "city", playerEmails);
		
		footballTeamService.saveFootballTeam(mapping);
	}

	@Test(expected=EntityNotFoundException.class)
	public void testExceptionThrownWhenPlayerDoesNotExist() throws EntityNotFoundException, EntityExistsException, PastCompetitionException{
		Set<String> playerEmails = new HashSet<>();
		playerEmails.add(PLAYER_BENTEKE.getEmail());
		playerEmails.add(PLAYER_UNKNOWN);
		
		FootballTeamRequestMapping mapping = 
				new FootballTeamRequestMapping(TEAM_ASTON_VILLA, OWNER_LAMBERT.getEmail(), COMPETITION.getName(), "city", playerEmails);
		
		footballTeamService.saveFootballTeam(mapping);
	}

	@Test(expected=EntityNotFoundException.class)
	public void testExceptionThrownWhenCompetitionDoesNotExist() throws EntityNotFoundException, EntityExistsException, PastCompetitionException{
		Set<String> playerEmails = new HashSet<>();
		playerEmails.add(PLAYER_BENTEKE.getEmail());
		playerEmails.add(PLAYER_HENRY.getEmail());
		
		FootballTeamRequestMapping mapping = 
				new FootballTeamRequestMapping(TEAM_ASTON_VILLA, OWNER_LAMBERT.getEmail(), COMPETITION_UNKNOWN, "city", playerEmails);
		
		footballTeamService.saveFootballTeam(mapping);
	}
	
	@Test(expected=PastCompetitionException.class)
	public void testPastCompetitionExceptionWhenCompetitionInPast() throws EntityNotFoundException, EntityExistsException, PastCompetitionException{
		Set<String> playerEmails = new HashSet<>();
		playerEmails.add(PLAYER_BENTEKE.getEmail());
		playerEmails.add(PLAYER_HENRY.getEmail());
		
		FootballTeamRequestMapping mapping = 
				new FootballTeamRequestMapping(TEAM_ASTON_VILLA, OWNER_LAMBERT.getEmail(), COMPETITION_PAST, "city", playerEmails);
		
		footballTeamService.saveFootballTeam(mapping);
	}
}

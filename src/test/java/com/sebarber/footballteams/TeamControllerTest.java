package com.sebarber.footballteams;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.Assert.*;

import com.sebarber.footballteams.entity.FootballTeamRequestMapping;

@Transactional
public class TeamControllerTest extends ControllerTest {
	private static final String TEAM_ASTON_VILLA = "Aston Villa";
	private static final String OWNER_EMAIL = "sam.barber@gmail.com";
	private static final String PLAYER_EMAIL_1 = "michael.owen@gmail.com";
	private static final String PLAYER_EMAIL_2 = "alan.shearer@gmail.com";
	private static final String PLAYER_NOT_EXIST = "dwayne.ledejo@gmail.com";
	private static final String COMPETITION = "FA Cup 2017";
	private static final String COMPETITION_PAST = "World Cup 1966";
	private static final String COMPETITION_NOT_EXIST = "COMPETITION_NOT_EXIST";	
	
	@Before
	public void setup(){
		super.setup();
	}
	
	
	@Test
	public void testCanCreateTeamGivenValidInput() throws Exception{		
		
		Set<String> emails = new HashSet<>();
		emails.add(PLAYER_EMAIL_1);
		emails.add(PLAYER_EMAIL_2);
		
		FootballTeamRequestMapping requestMapping = 
				new FootballTeamRequestMapping(TEAM_ASTON_VILLA, OWNER_EMAIL, COMPETITION, "London", emails);
		
		String json = jsonUtils.mapToJson(requestMapping);
		
		String uri = "/teams/save";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)).andReturn();
		
		String message = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		assertEquals(TEAM_ASTON_VILLA + " saved successfully.", message);
		assertEquals(HttpStatus.OK.value(), status);		
	}
	
	@Test
	public void testCannotCreateTeamGivenMissingPlayer() throws Exception{
		Set<String> emails = new HashSet<>();
		emails.add(PLAYER_EMAIL_1);
		emails.add(PLAYER_NOT_EXIST);
		
		FootballTeamRequestMapping requestMapping = 
				new FootballTeamRequestMapping(TEAM_ASTON_VILLA, OWNER_EMAIL, COMPETITION, "London", emails);
		
		String json = jsonUtils.mapToJson(requestMapping);
		
		String uri = "/teams/save";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)).andReturn();
		
		String message = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();		
		
		assertEquals(HttpStatus.PRECONDITION_FAILED.value(), status);
		assertEquals("Player with email " + PLAYER_NOT_EXIST + " does not exist", message);
	}
	
	@Test
	public void testCannotCreateTeamGivenMissingCompetition() throws Exception{
		Set<String> emails = new HashSet<>();
		emails.add(PLAYER_EMAIL_1);
		emails.add(PLAYER_EMAIL_2);
		
		FootballTeamRequestMapping requestMapping = 
				new FootballTeamRequestMapping(TEAM_ASTON_VILLA, OWNER_EMAIL, COMPETITION_NOT_EXIST, "London", emails);
		
		String json = jsonUtils.mapToJson(requestMapping);
		
		String uri = "/teams/save";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)).andReturn();
		
		String message = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();		
		
		assertEquals(HttpStatus.PRECONDITION_FAILED.value(), status);
		assertEquals("Competition " + COMPETITION_NOT_EXIST + " does not exist", message);
	}
	
	@Test
	public void testCannotCreateTeamGivenPastCompetition() throws Exception{
		Set<String> emails = new HashSet<>();
		emails.add(PLAYER_EMAIL_1);
		emails.add(PLAYER_EMAIL_2);
		
		FootballTeamRequestMapping requestMapping = 
				new FootballTeamRequestMapping(TEAM_ASTON_VILLA, OWNER_EMAIL, COMPETITION_PAST, "London", emails);
		
		String json = jsonUtils.mapToJson(requestMapping);
		
		String uri = "/teams/save";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)).andReturn();
		
		String message = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();		
		
		assertEquals(HttpStatus.PRECONDITION_FAILED.value(), status);
		assertEquals("Competition with name " + COMPETITION_PAST + " has already happened", message);
	}
	
	@Test
	public void testCannotCreateGivenMissingOwner() throws Exception{
		Set<String> emails = new HashSet<>();
		emails.add(PLAYER_EMAIL_1);
		emails.add(PLAYER_EMAIL_2);
		
		FootballTeamRequestMapping requestMapping = 
				new FootballTeamRequestMapping(TEAM_ASTON_VILLA, PLAYER_NOT_EXIST, COMPETITION, "London", emails);
		
		String json = jsonUtils.mapToJson(requestMapping);
		
		String uri = "/teams/save";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)).andReturn();
		
		String message = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();		
		
		assertEquals(HttpStatus.PRECONDITION_FAILED.value(), status);
		assertEquals("Player with email " + PLAYER_NOT_EXIST + " does not exist", message);
	}
}

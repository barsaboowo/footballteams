package com.sebarber.footballteams;

import java.util.Date;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.sebarber.footballteams.dao.CompetitionDAO;
import com.sebarber.footballteams.entity.Competition;
import com.sebarber.footballteams.service.CompetitionService;
import com.sebarber.footballteams.service.CompetitionServiceImpl;
import com.sebarber.footballteams.service.exceptions.PastCompetitionException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CompetitionServiceTest {
	
	private static final Long ONE_DAY_MILLIS = 24*60*60*1000L;
	
	private static final String COMPETITION_NAME_FUTURE = "FA Cup Future";
	private static final String COMPETITION_NAME_PAST = "FA Cup Past";
	private static final String COMPETITION_UNKNOWN = "COMPETITION_UNKNOWN";
	
	private static final Competition COMPETITION_PAST = 
			new Competition(COMPETITION_NAME_PAST, "Wembley", new Date(System.currentTimeMillis() - ONE_DAY_MILLIS));
	
	private static final Competition COMPETITION_FUTURE =
			new Competition(COMPETITION_NAME_FUTURE, "Wembley", new Date(System.currentTimeMillis() + ONE_DAY_MILLIS));	
	
	@Mock
	private CompetitionDAO competitionDAO;

	private CompetitionService competitionService;

	@Before
	public void before() {
		competitionDAO = Mockito.mock(CompetitionDAO.class);
		when(competitionDAO.findOne(COMPETITION_UNKNOWN)).thenReturn(null);
		when(competitionDAO.findOne(COMPETITION_NAME_FUTURE)).thenReturn(COMPETITION_FUTURE);
		when(competitionDAO.findOne(COMPETITION_NAME_PAST)).thenReturn(COMPETITION_PAST);
		
		competitionService = new CompetitionServiceImpl(competitionDAO);
	}
	
	@Test
	public void testCompetitionService() throws EntityNotFoundException, PastCompetitionException{
		Competition competition = competitionService.findFutureCompetitionByName(COMPETITION_NAME_FUTURE);
		
		assertEquals(competition, COMPETITION_FUTURE);
	}
	
	@Test(expected=PastCompetitionException.class)
	public void testExceptionThrownWhenCompetitionHasHappened() throws EntityNotFoundException, PastCompetitionException{
		competitionService.findFutureCompetitionByName(COMPETITION_NAME_PAST);
		
	}
	
	@Test(expected=EntityNotFoundException.class)
	public void testExceptionThrownWhenCompetitionNotFound() throws EntityNotFoundException, PastCompetitionException{
		competitionService.findFutureCompetitionByName(COMPETITION_UNKNOWN);		
	}

}

package com.sebarber.footballteams.service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import com.sebarber.footballteams.entity.FootballTeam;
import com.sebarber.footballteams.entity.FootballTeamRequestMapping;
import com.sebarber.footballteams.service.exceptions.PastCompetitionException;

public interface FootballTeamService extends Service<FootballTeam, String> {
	void saveFootballTeam(FootballTeamRequestMapping team) throws EntityNotFoundException, EntityExistsException, PastCompetitionException ;
}

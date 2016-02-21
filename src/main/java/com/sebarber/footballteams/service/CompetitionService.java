package com.sebarber.footballteams.service;

import javax.persistence.EntityNotFoundException;

import com.sebarber.footballteams.entity.Competition;
import com.sebarber.footballteams.service.exceptions.PastCompetitionException;

public interface CompetitionService extends Service<Competition, String> {

	Competition findFutureCompetitionByName(String competition) throws EntityNotFoundException, PastCompetitionException;

}

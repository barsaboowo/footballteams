package com.sebarber.footballteams.service;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.sebarber.footballteams.dao.CompetitionDAO;
import com.sebarber.footballteams.entity.Competition;
import com.sebarber.footballteams.service.exceptions.PastCompetitionException;

public class CompetitionServiceImpl implements CompetitionService{
	private static final Logger LOG = Logger.getLogger(CompetitionServiceImpl.class);
	
	
	public CompetitionServiceImpl() {
		super();
	}

	public CompetitionServiceImpl(CompetitionDAO competitionDAO) {
		super();
		this.competitionDAO = competitionDAO;
	}

	@Autowired
	private CompetitionDAO competitionDAO;

	@Override
	public Collection<Competition> findAll() {
		LOG.info("Finding all competition entries");
		return competitionDAO.findAll();
	}

	@Override
	public Competition get(String key) {
		LOG.info("Finding competition with name " + key);
		return competitionDAO.findOne(key);
	}

	@Override
	public void save(String key, Competition entity) {
		LOG.info("Saving competition with key " + key);
		competitionDAO.saveAndFlush(entity);
		LOG.info("Saved successfully");
	}

	@Override
	public Competition findFutureCompetitionByName(String competitionName) throws EntityNotFoundException, PastCompetitionException {
		LOG.info("Finding a future competition with name " + competitionName);
		Competition competition = competitionDAO.findOne(competitionName);
		
		if(competition == null){
			LOG.warn("No competition found with name " + competitionName);
			throw new EntityNotFoundException("Competition " + competitionName + " does not exist");
		}else if(competition.getDate().before(new Date())){
			String message = "Competition with name " + competitionName + " has already happened";
			LOG.warn(message);
			throw new PastCompetitionException(message);
		}else{
			LOG.info("Competition found for name " + competitionName);
			return competition;
		}
		
	}

}

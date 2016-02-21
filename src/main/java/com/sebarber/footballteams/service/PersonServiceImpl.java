package com.sebarber.footballteams.service;

import java.util.Collection;

import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.sebarber.footballteams.dao.PersonDAO;
import com.sebarber.footballteams.entity.Person;

public class PersonServiceImpl implements PersonService {
	private static final Logger LOG = Logger.getLogger(PersonServiceImpl.class);
	
	@Autowired
	private PersonDAO personDAO;

	@Override
	public Collection<Person> findAll() {
		LOG.info("Finding all person entries");
		return personDAO.findAll();
	}

	@Override
	public Person get(String key) throws EntityNotFoundException {
		LOG.info("Finding person entries for key " + key);
		Person p = personDAO.findOne(key);
		
		if(p == null){
			String message = "Player with email " + key + " does not exist";
			LOG.warn(message);
			throw new EntityNotFoundException(message);
		}

		return p;
	}

	@Override
	public void save(String key, Person entity) {
		LOG.info("Saving person with id " + key);
		personDAO.saveAndFlush(entity);
		
	}
	
	

}

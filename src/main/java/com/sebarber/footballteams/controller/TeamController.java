package com.sebarber.footballteams.controller;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sebarber.footballteams.entity.FootballTeam;
import com.sebarber.footballteams.entity.FootballTeamRequestMapping;
import com.sebarber.footballteams.service.FootballTeamService;
import com.sebarber.footballteams.service.exceptions.PastCompetitionException;

@RestController
@RequestMapping("/teams")
public class TeamController {
	private static final Logger LOG = Logger.getLogger(TeamController.class);

	@Autowired
	private FootballTeamService footballTeamService;	

	@RequestMapping("/list")
	public Collection<FootballTeam> getTeams() {
		return footballTeamService.findAll();
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<String> create(@RequestBody FootballTeamRequestMapping team) {
		ResponseEntity<String> response = null;

	    if(team != null){
	    	try {
				footballTeamService.saveFootballTeam(team);
			} catch (EntityNotFoundException e) {
				LOG.warn("Entity not found: " + e.getMessage());
				response = new ResponseEntity<String>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
			} catch (EntityExistsException e) {
				LOG.warn("Entity exists: " + e.getMessage());
				response = new ResponseEntity<String>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
			} catch (PastCompetitionException e) {
				LOG.warn("Past competition: " + e.getMessage());
				response = new ResponseEntity<String>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
			} catch(Exception e){
				LOG.error("Unexpected exception", e);
				response = new ResponseEntity<>("A Server error has occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
			}
	    }
	    if(response == null){
	    	response = new ResponseEntity<String>(team.getName() + " saved successfully.", HttpStatus.OK);
	    }
	    
	    return response;
	}

}

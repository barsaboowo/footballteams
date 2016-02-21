package com.sebarber.footballteams.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sebarber.footballteams.entity.Competition;
import com.sebarber.footballteams.service.CompetitionService;

@RequestMapping("/competitions")
@RestController
public class CompetitionController {
	
	@Autowired
	private CompetitionService competitionService;
	
	
	@RequestMapping("/list")
	public Collection<Competition> listCompetitions(){
		return competitionService.findAll();
	}
}

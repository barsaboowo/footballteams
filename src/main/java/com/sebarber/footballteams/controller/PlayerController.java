package com.sebarber.footballteams.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sebarber.footballteams.entity.Person;
import com.sebarber.footballteams.service.PersonService;

@RestController
@RequestMapping("/players")
public class PlayerController {
	
	@Autowired
	private PersonService personService;
	
	@RequestMapping("/list")
	public Collection<Person> listPlayers(){
		return personService.findAll();
	}
}

package com.sebarber.footballteams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sebarber.footballteams.service.CompetitionService;
import com.sebarber.footballteams.service.CompetitionServiceImpl;
import com.sebarber.footballteams.service.FootballTeamService;
import com.sebarber.footballteams.service.FootballTeamServiceImpl;
import com.sebarber.footballteams.service.PersonService;
import com.sebarber.footballteams.service.PersonServiceImpl;

@SpringBootApplication
public class FootballteamsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballteamsApplication.class, args);
	}
	
	@Bean
	public FootballTeamService footballTeamService(){
		return new FootballTeamServiceImpl();
	}
	
	@Bean
	public PersonService personService(){
		return new PersonServiceImpl();
	}
	
	@Bean
	public CompetitionService competitionService(){
		return new CompetitionServiceImpl();
	}
}

package com.sebarber.footballteams.dao;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sebarber.footballteams.entity.Competition;

@ComponentScan
public interface CompetitionDAO extends JpaRepository<Competition, String>{
	
	

}

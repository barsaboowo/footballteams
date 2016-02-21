package com.sebarber.footballteams.dao;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sebarber.footballteams.entity.Person;

@ComponentScan
public interface PersonDAO extends JpaRepository<Person, String> {

}

package com.sebarber.footballteams.service;

import java.util.Collection;

import javax.persistence.EntityNotFoundException;


public interface Service<E, K> {
	
	Collection<E> findAll();
	E get(K key) throws EntityNotFoundException;
	void save(K key, E entity);

}

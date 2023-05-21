package com.dam.springboot.backend.service;

import java.util.List;
import java.util.Optional;

public interface ICRUD <T,E> {
	List<T> findAll();
	void delete(E id);
	Optional<T> findById(E id);
	T save (T entity);
}

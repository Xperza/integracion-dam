package com.dam.springboot.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dam.springboot.backend.entities.Proyecto;
import com.dam.springboot.backend.entities.Tarea;
import com.dam.springboot.backend.entities.Usuario;
import com.dam.springboot.backend.repository.TareaRepository;

@Service
public class TareaService implements ICRUD<Tarea, Integer> {
	
	@Autowired
	private TareaRepository tareaRepository;
	
	@Override
	public List<Tarea> findAll() {
		return tareaRepository.findAll();
	}

	@Override
	public void delete(Integer id) {
		tareaRepository.deleteById(id);
	}

	@Override
	public Optional<Tarea> findById(Integer id) {
		return tareaRepository.findById(id);
	}

	@Override
	public Tarea save(Tarea entity) {
		return tareaRepository.save(entity);
	}

	public List<Tarea> findByUsuarios(Usuario usuario){
		return tareaRepository.findAllByUsuarios(usuario);
	}
	
	public List<Tarea> findByProyecto(Proyecto proyecto){
		return tareaRepository.findAllByProyecto(proyecto);
	}
}

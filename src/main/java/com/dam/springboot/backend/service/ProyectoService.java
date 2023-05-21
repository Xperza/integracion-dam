package com.dam.springboot.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dam.springboot.backend.entities.Proyecto;
import com.dam.springboot.backend.entities.Usuario;
import com.dam.springboot.backend.repository.ProyectoRepository;

@Service
public class ProyectoService implements ICRUD<Proyecto, Integer> {

	@Autowired
	private ProyectoRepository proyectoRepository;
	
	@Override
	public List<Proyecto> findAll() {
		return proyectoRepository.findAll();
	}

	@Override
	public void delete(Integer id) {
		proyectoRepository.deleteById(id);
	}

	@Override
	public Optional<Proyecto> findById(Integer id) {
		return proyectoRepository.findById(id);
	}

	@Override
	public Proyecto save(Proyecto entity) {
		return proyectoRepository.save(entity);
	}
	
	public List<Proyecto> findByUsuario(Usuario usuario){
		return proyectoRepository.findAllByUsuarios(usuario);
	}

}

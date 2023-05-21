package com.dam.springboot.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dam.springboot.backend.entities.Proyecto;
import com.dam.springboot.backend.entities.Tarea;
import com.dam.springboot.backend.entities.Usuario;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {
	List<Tarea>findAllByUsuarios(Usuario usuario);
	List<Tarea>findAllByProyecto(Proyecto proyecto);
}

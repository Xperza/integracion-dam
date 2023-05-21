package com.dam.springboot.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dam.springboot.backend.dto.TareaDTO;
import com.dam.springboot.backend.entities.Tarea;
import com.dam.springboot.backend.mapper.TareaMapper;
import com.dam.springboot.backend.service.ProyectoService;
import com.dam.springboot.backend.service.TareaService;
import com.dam.springboot.backend.service.UsuarioService;

@RestController
@RequestMapping("/tareas")
@CrossOrigin(origins = {"http://localhost:4200"})
public class TareaController {

	@Autowired
	TareaMapper tareaMapper;
	
	@Autowired
	TareaService tareaService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ProyectoService proyectoService;
	
	@GetMapping
	public List<TareaDTO> getTareaList() {
		return tareaMapper.changeListToDTO(tareaService.findAll());
	}

	@GetMapping("/{id}")
	public TareaDTO getTareaById(@PathVariable("id") Integer id) {
		return tareaMapper.toTareaDTO(tareaService.findById(id).get());
	}
	
	@GetMapping("/user/{id}")
	public List<TareaDTO> getTareasByUserId(@PathVariable Integer id) {
		return tareaMapper.changeListToDTO(tareaService.findByUsuarios(usuarioService.findById(id).get()));
	}
	
	@GetMapping("/proyecto/{id}")
	public List<TareaDTO> getTareasByProyectoId(@PathVariable Integer id) {
		return tareaMapper.changeListToDTO(tareaService.findByProyecto(proyectoService.findById(id).get()));
	}
	
	@PostMapping
	public ResponseEntity<?> addTarea(@RequestBody TareaDTO tareaDTO) {
		Map<String, Object> responseMap = new HashMap<>();
		try {
			tareaService.save(tareaMapper.toEntity(tareaDTO));
		} catch (DataAccessException e) {
			responseMap.put("mensaje", "Error al añadir en la base de datos");
			responseMap.put("error", e.getMessage().concat(": " + e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		responseMap.put("mensaje", "La tarea ha sido añadida correctamente");
		responseMap.put("tarea", tareaDTO);
		return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateTarea(@PathVariable Integer id, @RequestBody TareaDTO tareaDTO) {
		Map<String, Object> responseMap = new HashMap<>();
		Optional<Tarea> tareaActual = tareaService.findById(id);
		if (tareaActual.isPresent()) {
			Tarea tarea = tareaMapper.toEntity(tareaDTO);
			tarea.setId(id);
			tareaService.save(tarea);
			responseMap.put("mensaje", "La tarea ha sido actualizada correctamente");
			responseMap.put("tarea", tareaMapper.toTareaDTO(tarea));
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Map<String, Object>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTarea(@PathVariable Integer id) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            tareaService.delete(id);
            responseMap.put("mensaje", "La tarea ha sido eliminada correctamente");
            return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.OK);
        } catch (DataAccessException e) {
            responseMap.put("mensaje", "Error al eliminar en la base de datos");
            responseMap.put("error", e.getMessage().concat(": " + e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

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

import com.dam.springboot.backend.dto.ProyectoDTO;
import com.dam.springboot.backend.dto.TareaDTO;
import com.dam.springboot.backend.entities.Proyecto;
import com.dam.springboot.backend.mapper.ProyectoMapper;
import com.dam.springboot.backend.mapper.TareaMapper;
import com.dam.springboot.backend.service.ProyectoService;
import com.dam.springboot.backend.service.UsuarioService;

@RestController
@RequestMapping("/proyectos")
@CrossOrigin(origins = { "http://localhost:4200" })
public class ProyectoController {

	@Autowired
	ProyectoMapper proyectoMapper;

	@Autowired
	ProyectoService proyectoService;

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	TareaMapper tareaMapper;

	@GetMapping
	public List<ProyectoDTO> getProyectoList() {
		return proyectoMapper.changeListToDTO(proyectoService.findAll());
	}

	@GetMapping("/{id}")
	public ProyectoDTO getProyectoById(@PathVariable("id") Integer id) {
		return proyectoMapper.toProyectoDTO(proyectoService.findById(id).get());
	}
	
	@GetMapping("/user/{id}")
	public List<ProyectoDTO> getProyectosByUserId(@PathVariable Integer id) {
		return proyectoMapper.changeListToDTO(proyectoService.findByUsuario(usuarioService.findById(id).get()));
	} 
	
	@GetMapping("/{id}/tareas")
	public List<TareaDTO> getTareasByProyecto(@PathVariable Integer id) {
		Proyecto proyecto = proyectoService.findById(id).get();
		return tareaMapper.changeListToDTO(proyecto.getTareas());
	}

	@PostMapping
	public ResponseEntity<?> addProyecto(@RequestBody ProyectoDTO proyectoDTO) {
		Map<String, Object> responseMap = new HashMap<>();
		try {
			proyectoService.save(proyectoMapper.toEntity(proyectoDTO));
		} catch (DataAccessException e) {
			responseMap.put("mensaje", "Error al añadir en la base de datos");
			responseMap.put("error", e.getMessage().concat(": " + e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		responseMap.put("mensaje", "El proyecto ha sido añadido correctamente");
		responseMap.put("proyecto", proyectoDTO);
		return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProyecto(@PathVariable Integer id, @RequestBody ProyectoDTO proyectoDTO) {
		Map<String, Object> responseMap = new HashMap<>();
		Optional<Proyecto> proyectoActual = proyectoService.findById(id);
		if (proyectoActual.isPresent()) {
			Proyecto proyecto = proyectoMapper.toEntity(proyectoDTO);
			proyecto.setId(id);
			proyectoService.save(proyecto);
			responseMap.put("mensaje", "El proyecto ha sido actualizado correctamente");
			responseMap.put("proyecto", proyectoMapper.toProyectoDTO(proyecto));
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Map<String, Object>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProyecto(@PathVariable Integer id) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            proyectoService.delete(id);
            responseMap.put("mensaje", "El proyecto ha sido eliminado correctamente");
            return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.OK);
        } catch (DataAccessException e) {
            responseMap.put("mensaje", "Error al eliminar en la base de datos");
            responseMap.put("error", e.getMessage().concat(": " + e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

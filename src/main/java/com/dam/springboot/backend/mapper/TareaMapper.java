package com.dam.springboot.backend.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dam.springboot.backend.dto.TareaDTO;
import com.dam.springboot.backend.entities.Tarea;
import com.dam.springboot.backend.entities.Usuario;
import com.dam.springboot.backend.service.UsuarioService;

@Component
public class TareaMapper {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ProyectoMapper proyectoMapper;
	
	public List<String> getEmailUsuarios(Tarea tarea){
		
		List<String> usuarios = new ArrayList<>();
		
		for(Usuario usuario: tarea.getUsuarios()) 
			usuarios.add(usuario.getEmail());
		
		return usuarios;
	}
	
	public List<Usuario> getListUsuarios(List<Integer> usuarios){
		
		List<Usuario> users = new ArrayList<>();
		
		for(Integer usuario:usuarios) {
			Optional<Usuario> u = usuarioService.findById(usuario);
			if(u.isPresent())
			users.add(usuarioService.findById(usuario).get());
		}
		
		return users;
	}
	
	public TareaDTO toTareaDTO(Tarea tarea) {
		
		TareaDTO dto = new TareaDTO();
		
		dto.setId(tarea.getId());
		dto.setFecha(tarea.getFecha());
		dto.setFechaFin(tarea.getFechaFin());
		dto.setImportancia(tarea.getImportancia());
		dto.setNombre(tarea.getNombre());
		dto.setDescripcion(tarea.getDescripcion());
		dto.setProyecto(proyectoMapper.toProyectoDTO(tarea.getProyecto()));
		dto.setUsuario(getEmailUsuarios(tarea));
		
		return dto;
	}
	
	public Tarea toEntity(TareaDTO dto) {
		Tarea tarea = new Tarea();
		
		tarea.setId(dto.getId());
		tarea.setDescripcion(dto.getDescripcion());
		tarea.setFecha(dto.getFecha());
		tarea.setNombre(dto.getNombre());
		tarea.setFechaFin(dto.getFechaFin());
		tarea.setImportancia(dto.getImportancia());
		tarea.setProyecto(proyectoMapper.toEntity(dto.getProyecto()));
		
		if (dto.getUsuario()!=null) {
			List<Usuario> usuarios = new ArrayList<>();
			
			for(String usuarioDTO:dto.getUsuario()) {
				Usuario usuario = usuarioService.findOneByEmail(usuarioDTO).get();
				usuarios.add(usuario);
			}				
			tarea.setUsuarios(usuarios);
		}
		
		return tarea;
	}
	
	public List<TareaDTO> changeListToDTO(List<Tarea> lista) {
		List<TareaDTO> newListaDtos = new ArrayList<>();
		for (int i = 0; i < lista.size(); i++) {
			Tarea tarea = lista.get(i);
			newListaDtos.add(toTareaDTO(tarea));
		}
		return newListaDtos;
	}
}

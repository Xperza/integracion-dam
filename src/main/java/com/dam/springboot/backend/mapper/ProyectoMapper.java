package com.dam.springboot.backend.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dam.springboot.backend.dto.ProyectoDTO;
import com.dam.springboot.backend.entities.Proyecto;
import com.dam.springboot.backend.entities.Usuario;
import com.dam.springboot.backend.service.UsuarioService;

@Component
public class ProyectoMapper {

	@Autowired
	private UsuarioService usuarioService;
	
	public List<String> getEmailUsuarios(Proyecto proyecto){
		
		List<String> usuarios = new ArrayList<>();
		
		for(Usuario usuario: proyecto.getUsuarios()) 
			usuarios.add(usuario.getEmail());
		
		return usuarios;
	}
	
	public ProyectoDTO toProyectoDTO(Proyecto proyecto) {
		
		ProyectoDTO dto = new ProyectoDTO();
		
		dto.setId(proyecto.getId());
		dto.setDescripcion(proyecto.getDescripcion());
		dto.setFecha(proyecto.getFecha());
		dto.setNombre(proyecto.getNombre());
		dto.setUsuarios(getEmailUsuarios(proyecto));
		
		return dto;
	}
	
	public Proyecto toEntity(ProyectoDTO dto) {
		Proyecto proyecto = new Proyecto();
		
		proyecto.setId(dto.getId());
		proyecto.setDescripcion(dto.getDescripcion());
		proyecto.setFecha(dto.getFecha());
		proyecto.setNombre(dto.getNombre());
		
		if (dto.getUsuarios()!=null) {
			List<Usuario> usuarios = new ArrayList<>();
			
			for(String usuarioDTO:dto.getUsuarios()) {
				Usuario usuario = usuarioService.findOneByEmail(usuarioDTO).get();
				usuarios.add(usuario);
			}				
			proyecto.setUsuarios(usuarios);
		}
		
		return proyecto;
	}
	
	public List<ProyectoDTO> changeListToDTO(List<Proyecto> lista) {
		List<ProyectoDTO> newListaDtos = new ArrayList<>();
		for (int i = 0; i < lista.size(); i++) {
			Proyecto proyecto = lista.get(i);
			newListaDtos.add(toProyectoDTO(proyecto));
		}
		return newListaDtos;
	}
}

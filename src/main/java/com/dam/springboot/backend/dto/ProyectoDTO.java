package com.dam.springboot.backend.dto;

import java.util.Date;
import java.util.List;

public class ProyectoDTO {

	private int id;
	private String nombre;
	private Date fecha;
	private String descripcion;
	private List<String> usuarios;
	
	public ProyectoDTO() {
	}

	public int getId() {
		return id;
	}

	public ProyectoDTO(int id, String nombre, Date fecha, String descripcion, List<String> usuarios) {
		this.id = id;
		this.nombre = nombre;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.usuarios = usuarios;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<String> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<String> usuarios) {
		this.usuarios = usuarios;
	}	
}

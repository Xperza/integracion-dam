package com.dam.springboot.backend.dto;

import java.util.Date;
import java.util.List;

public class TareaDTO {

	private int id;
	private String nombre;
	private String importancia;
	private String descripcion;
	private Date fecha;
	private Date fechaFin;
	private ProyectoDTO proyecto;
	private List<String> usuario;
	private boolean estado;
	
	public TareaDTO() {
	}

	public TareaDTO(int id, String nombre, String importancia, Date fecha, Date fechaFin, ProyectoDTO proyecto,
			List<String> usuario, String descripcion, boolean estado) {
		this.id = id;
		this.nombre = nombre;
		this.importancia = importancia;
		this.fecha = fecha;
		this.fechaFin = fechaFin;
		this.proyecto = proyecto;
		this.usuario = usuario;
		this.descripcion = descripcion;
		this.estado=estado;
	}

	public int getId() {
		return id;
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

	public String getImportancia() {
		return importancia;
	}

	public void setImportancia(String importancia) {
		this.importancia = importancia;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public ProyectoDTO getProyecto() {
		return proyecto;
	}

	public void setProyecto(ProyectoDTO proyecto) {
		this.proyecto = proyecto;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public List<String> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<String> usuario) {
		this.usuario = usuario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}

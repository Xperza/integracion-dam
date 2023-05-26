package com.dam.springboot.backend.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "proyectos")
@NamedQuery(name="Proyecto.findAll", query="SELECT p FROM Proyecto p")
public class Proyecto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_proyecto")
	private int id;

	@NotEmpty(message = "No puede estar vacio")
	private String nombre;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	private String descripcion;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name="proyecto_usuario",
			joinColumns = @JoinColumn(name = "id_proyecto"),
			inverseJoinColumns = @JoinColumn(name = "id_usuario"))
	private List<Usuario> usuarios;
	
	@OneToMany(mappedBy="proyecto", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Tarea> tareas;

	public Proyecto() {
	}

	public Proyecto(int id, String nombre, Date fecha, String descripcion, List<Usuario> usuarios, List<Tarea> tareas) {
		this.id = id;
		this.nombre = nombre;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.usuarios = usuarios;
		this.tareas = tareas;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}
	
	public Tarea addTarea(Tarea tarea) {
		getTareas().add(tarea);
		tarea.setProyecto(this);
		
		return tarea;
	}
	
	public Tarea deleteTarea(Tarea tarea) {
		getTareas().remove(tarea);
		tarea.setProyecto(this);
		
		return tarea;
	}
}

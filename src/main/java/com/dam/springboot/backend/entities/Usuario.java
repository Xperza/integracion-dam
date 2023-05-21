package com.dam.springboot.backend.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private int id;
	
	@NotEmpty(message = "No puede estar vacio")
	private String nombre;
	
	@NotEmpty(message = "No puede estar vacio")
	private String apellidos;
	
	@NotEmpty(message = "No puede estar vacio")
	@Email
	private String email;
	
	@NotEmpty(message = "No puede estar vacio")
	@Size(min = 8, message = "El tamaño tiene que tener minimo 8")
	@Pattern(regexp = "^(?=.*[A-Z]).+$", message = "La contraseña debe contener al menos una letra mayúscula")
	@Pattern(regexp = "^(?=.*[a-z]).+$", message = "La contraseña debe contener al menos una letra minúscula")
	@Pattern(regexp = "^(?=.*\\d).+$", message = "La contraseña debe contener al menos una número")
	@Pattern(regexp = "^(?=.*[!@#$%^&*()\\-+_=\\[\\]{}\\\\|;:'\",.<>/?]).+$", message = "La contraseña debe contener al menos un caracter especial")
	private String password;
	
	@ManyToMany(mappedBy="usuarios")
	private List<Proyecto> proyectos;
	
	@ManyToMany(mappedBy="usuarios")
	private List<Tarea> tareas;
	
	public Usuario() {
	}
	
	public Usuario(int id, String nombre, String apellidos, String email, String password, List<Proyecto> proyectos,
			List<Tarea> tareas) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.password = password;
		this.proyectos = proyectos;
		this.tareas = tareas;
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}
	
	public List<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}
}

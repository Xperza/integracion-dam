package com.dam.springboot.backend.dto;

public class UsuarioDTO {

	private int id;
	private String apellidos;
	private String nombre;
	private String email;
	private String password;	
	
	public UsuarioDTO() {
	}
	
	public UsuarioDTO(int id, String apellidos, String nombre, String email, String password) {
		this.id = id;
		this.apellidos = apellidos;
		this.nombre = nombre;
		this.email = email;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
}

package edu.eci.arsw.LibraryAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Libreria {
	private int id;
	private String nombre;
	private String direccion;
	private String telefono;
	private HashMap<Integer, Libro> libros;
	
	
	public Libreria(int id, String nombre, String direccion, String telefono) {
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.libros = new HashMap<>();
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
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void saveBook(Libro libro) {
		libros.put(libro.getId(), libro);
	}
	public HashMap<Integer, Libro> getLibros() {
		return this.libros;
	}

	
}

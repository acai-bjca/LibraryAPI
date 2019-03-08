package edu.eci.arsw.LibraryAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.spi.TransactionalWriter;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@Service
public class LibraryServices {
	private HashMap<Integer, Libreria> librerias;
	
	public LibraryServices() {	
		super();
		this.librerias = new HashMap<>();
		Libro l1 = new Libro(1, "Cien años de soledad", "Gabriel Garcia Marquez", "dasdsadsadadsa");
		Libro l2 = new Libro(2, "Maria", "Jorge Isaacs", "dasdsadsadadsa");
		Libro l3 = new Libro(3, "La iliada", "Homero", "dasdsadsadadsa");
		Libro l4 = new Libro(4, "El juego edl angel", "No se", "dasdsadsadadsa");
		Libreria lib1 = new Libreria(1, "Primer libreria", "cra 4 a #11-12", "4059784");		
		Libreria lib2 = new Libreria(2, "Segunda libreria", "calle 24 B #11-12 C", "9874586");
		lib1.saveBook(l1);
		lib1.saveBook(l2);
		lib2.saveBook(l3);
		lib2.saveBook(l4);	
		librerias.put(1, lib1);
		librerias.put(2, lib2);
	}

	public void saveLibreria(Libreria libreria) throws LibraryException {
		if(librerias.containsKey(libreria.getId())){
			throw new LibraryException("Ya exite esta libreria con id "+libreria.getId());
		}else {
			librerias.put(libreria.getId(), libreria);
		}
	}
	
	public void saveBook(int id, Libro libro) throws LibraryException {
		if(!librerias.containsKey(id)){
			throw new LibraryException("No existe una libreria con id: "+id);
		}else {
			Libreria lib = librerias.get(id);
			if(lib.getLibros().containsKey(libro.getId())) {
				throw new LibraryException("Ya existe un libro con id: "+id+" en la libreria con id: "+id);
			}else {
				lib.saveBook(libro);
			}
		}
	}
	
	public void saveBookAndSendMail(int id, Libro libro, String correo) throws LibraryException {
		if(!librerias.containsKey(id)){
			throw new LibraryException("No existe una libreria con id: "+id);
		}else {
			Libreria lib = librerias.get(id);			
			if(lib.getLibros().containsKey(libro.getId())) {
				throw new LibraryException("Ya existe un libro con id: "+id+" en la libreria con id: "+id);
			}else {
				lib.saveBook(libro);
				LibraryThread hilo = new LibraryThread(correo);
				hilo.start();
			}
		}
	}
	
	public HashMap<Integer, Libro> getLibros(int id) throws LibraryException {
		if(!librerias.containsKey(id)){
			throw new LibraryException("No existe una libreria con id: "+id);
		}else {
			Libreria lib = librerias.get(id);
			return lib.getLibros();
		}
		
	}
	
	public void deleteLibreria(int id) throws LibraryException {		
		if(!librerias.containsKey(id)){
			throw new LibraryException("No existe una libreria con id: "+id);
		}else {
			Libreria lib = librerias.get(id);		
			if(lib.getLibros().isEmpty()) {
				librerias.remove(id);
			}else {
				throw new LibraryException("La libreria "+id+" tiene libros.");
			}
		}
	}
	
	public HashMap<Integer, Libreria> getTodasLibrerias() {
		return librerias;
	}
	
}

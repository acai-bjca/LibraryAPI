package edu.eci.arsw.LibraryAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/Library")
public class LibraryController {
	
	@Autowired
	private LibraryServices services;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Libreria>> manejadorGetRecursoLibrerias(){
		HashMap<Integer, Libreria> libreriasS =  services.getTodasLibrerias();
		List<Libreria> librerias = new ArrayList<>();
		for(Libreria l: libreriasS.values()) {
			librerias.add(l);
		}
		return new ResponseEntity<>(librerias, HttpStatus.OK);
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Libro>> manejadorGetRecursoLibros(@PathVariable int id){		
		HashMap<Integer, Libro> librosh;
		try {
			librosh = services.getLibros(id);
			List<Libro> libros = new ArrayList<>();
			for(Libro l: librosh.values()) {
				libros.add(l);
			}
			return new ResponseEntity<>(libros, HttpStatus.OK);
		} catch (LibraryException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);			
		}
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> manejadorPosRecursoLibreria(@RequestBody Libreria libreria){
		try {
			services.saveLibreria(libreria);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (LibraryException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.POST)
	public ResponseEntity<?> manejadorPosRecursoLibro(@RequestBody Libro libro, @PathVariable int id){
		try {
			services.saveBook(id, libro);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (LibraryException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="{id}/{correo}", method = RequestMethod.POST)
	public ResponseEntity<?> manejadorPosRecursoLibroCorreo(@RequestBody Libro libro, @PathVariable int id, @PathVariable String correo){
		try {
			services.saveBookAndSendMail(id, libro, correo);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (LibraryException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> manejadorPosRecursoLibro(@PathVariable int id){
		try {
			services.deleteLibreria(id);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (LibraryException e) {
			if(e.getMessage().equals("No existe una libreria con id: "+id)) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else{
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		}
	}
}

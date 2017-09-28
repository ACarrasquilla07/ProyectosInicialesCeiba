package rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import dominio.Bibliotecario;
import dominio.Libro;
import dominio.Prestamo;
import rest.dto.PeticionPrestamo;

@EnableAutoConfiguration
@RestController
@Transactional
public class Controlador {

	@Autowired
	Bibliotecario bibliotecario;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

	@RequestMapping(value = "/prestamo", method = RequestMethod.POST)
	@ResponseBody
	public Prestamo servicioPrestar(@RequestBody PeticionPrestamo peticionPrestamo) {
		bibliotecario.prestar(peticionPrestamo.getIsbn(), peticionPrestamo.getNombreUsuario()); 
		return bibliotecario.obtenerPestamoPorIsbn(peticionPrestamo.getIsbn());
	}

	@RequestMapping(value = "/crealibro", method = RequestMethod.POST)
	@ResponseBody
	public Libro crearLibroServicio(@RequestBody Libro libro) {
		return bibliotecario.ingresarLibro(libro);
	}

	@RequestMapping(value = "/listalibros", method = RequestMethod.GET)
	@ResponseBody
	public List<Libro> listarLibros() {
		return bibliotecario.listarLibros();
	}

	@RequestMapping(value = "/listaprestados", method = RequestMethod.GET)
	@ResponseBody
	public List<Libro> listarPrestados() {
		return bibliotecario.listarLibrosPrestados();
	}
}
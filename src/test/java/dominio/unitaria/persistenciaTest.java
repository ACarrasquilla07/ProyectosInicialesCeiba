package dominio.unitaria;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dominio.Libro;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;
import persistencia.builder.LibroBuilder;
import persistencia.entitad.LibroEntity;
import persistencia.sistema.SistemaDePersistencia;

public class persistenciaTest {
	
	@Test
	public void listarLibrosTest() {
		SistemaDePersistencia sistemaPersistencia = new SistemaDePersistencia();
		RepositorioLibro repositorioLibros = sistemaPersistencia.obtenerRepositorioLibros();
		RepositorioPrestamo repositorioPrestamo = sistemaPersistencia.obtenerRepositorioPrestamos();
		sistemaPersistencia.iniciar();
		
		Libro libro = new Libro("123a", "Soledad", 1999);
		repositorioLibros.agregar(libro);
		Libro libro2 = new Libro("123b", "Policias", 1989);
		repositorioLibros.agregar(libro);
		
		
		List<Libro> listaLibros = new ArrayList<>();
		listaLibros = repositorioLibros.obtenerListaLibros();
		assertEquals(2, listaLibros.size());
		
		Libro libro3 = new Libro("123c", "Loros", 1987);
		repositorioLibros.agregar(libro);
		
		listaLibros = repositorioLibros.obtenerListaLibros();
		assertEquals(3, listaLibros.size());
		
		sistemaPersistencia.terminar();
	}
}

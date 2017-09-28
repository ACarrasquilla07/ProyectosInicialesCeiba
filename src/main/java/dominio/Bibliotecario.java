package dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dominio.excepcion.PrestamoException;
import dominio.reglas.ReglaFechaLimite;
import dominio.reglas.ReglaNoPrestarDomingo;
import dominio.reglas.ReglaPalindromo;
import dominio.reglas.ReglaVerificarSiPrestado;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

public class Bibliotecario {
	public static final String MSJ_LIBRO_NO_EXISTE = "El libro no existe en la biblioteca";
	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
	public static final int SUMA_DE_NUM_ISBN_MAX = 30;
	private RepositorioLibro repositorioLibro;
	private RepositorioPrestamo repositorioPrestamo;
	private List<ReglaPosibilidadPrestamo> reglasPosibiliadPrestamo = new ArrayList<>();

	public Bibliotecario() {
		agregarReglas();
	}

	public Bibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
		this.repositorioLibro = repositorioLibro;
		this.repositorioPrestamo = repositorioPrestamo;
		agregarReglas();
	}

	public void prestar(String isbn, String nombreUsuario) {
		Libro libroAPrestar = obtenerLibro(isbn);
		Date fechaSolicitud = new Date();
		Prestamo prestamo = new Prestamo(fechaSolicitud, libroAPrestar, null, nombreUsuario);
		for (ReglaPosibilidadPrestamo reglaPosibilidadPrestamo : reglasPosibiliadPrestamo) {
			if (reglaPosibilidadPrestamo.verificarPosibilidadDePrestamo(prestamo).isValido()) {
				prestamo = reglaPosibilidadPrestamo.verificarPosibilidadDePrestamo(prestamo);
			}
		}
		repositorioPrestamo.agregar(prestamo);
	}

	public List<Libro> listarLibros() {
		return repositorioLibro.obtenerListaLibros();
	}

	public List<Libro> listarLibrosPrestados() {
		return repositorioPrestamo.obtenerLibrosPrestados();
	}

	public boolean esPrestado(String isbn) {
		return (null != repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn));
	}

	public Libro obtenerLibro(String isbn) {
		Libro libro = null;
		try {
			libro = repositorioLibro.obtenerPorIsbn(isbn);
		} catch (Exception e) {
			throw new PrestamoException(MSJ_LIBRO_NO_EXISTE);
		}
		return libro;
	}
	
	public Prestamo obtenerPestamoPorIsbn(String isbn) {
		return repositorioPrestamo.obtener(isbn);
	}
	
	public Libro ingresarLibro(Libro libro) {
		Libro libroRepuesta = null;
		try {
			repositorioLibro.obtenerPorIsbn(libro.getIsbn());
		} catch (Exception e) {
			repositorioLibro.agregar(libro);
			libroRepuesta = repositorioLibro.obtenerPorIsbn(libro.getIsbn());
		}
		return libroRepuesta;
	}

	private void agregarReglas() {
		ReglaPosibilidadPrestamo regla0 = new ReglaVerificarSiPrestado(repositorioPrestamo);
		reglasPosibiliadPrestamo.add(regla0);
		ReglaPosibilidadPrestamo regla = new ReglaPalindromo();
		reglasPosibiliadPrestamo.add(regla);
		ReglaPosibilidadPrestamo regla2 = new ReglaNoPrestarDomingo();
		reglasPosibiliadPrestamo.add(regla2);
		ReglaPosibilidadPrestamo regla3 = new ReglaFechaLimite();
		reglasPosibiliadPrestamo.add(regla3);
	}

	public RepositorioPrestamo getRepositorioPrestamo() {
		return repositorioPrestamo;
	}

	
}

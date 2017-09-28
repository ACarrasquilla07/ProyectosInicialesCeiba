package dominio.integracion;

import static org.junit.Assert.fail;
import java.util.Calendar;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;

import dominio.Bibliotecario;
import dominio.Libro;
import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;
import persistencia.sistema.SistemaDePersistencia;
import rest.BibliotecarioApplicacion;
import testdatabuilder.LibroTestDataBuilder;

public class BibliotecarioCalificadorTest {

	private static final String CRONICA_DE_UNA_MUERTA_ANUNCIADA = "Cronica de una muerta anunciada";
	private SistemaDePersistencia sistemaPersistencia;

	private RepositorioLibro repositorioLibros;
	private RepositorioPrestamo repositorioPrestamo;

	@Before
	public void setUp() {

		sistemaPersistencia = new SistemaDePersistencia();

		repositorioLibros = sistemaPersistencia.obtenerRepositorioLibros();
		repositorioPrestamo = sistemaPersistencia.obtenerRepositorioPrestamos();

		sistemaPersistencia.iniciar();
	}

	@After
	public void tearDown() {
		sistemaPersistencia.terminar();
	}

	@Test
	public void prestarLibroTest() throws Exception {

		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).conIsbn("1234").build();
		repositorioLibros.agregar(libro);
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(libro.getIsbn(), "Juan");

		// assert
		Assert.assertTrue(blibliotecario.esPrestado(libro.getIsbn()));
		Assert.assertNotNull(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn()));

	}

	@Test
	public void prestarLibroNoDisponibleTest() throws Exception {

		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).build();

		repositorioLibros.agregar(libro);

		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(libro.getIsbn(), "Juan");
		try {

			blibliotecario.prestar(libro.getIsbn(), "Juan");
			fail();

		} catch (PrestamoException e) {
			// assert
			Assert.assertEquals(Bibliotecario.EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE, e.getMessage());
		}
	}

	@Test
	public void prestarLibroPalindromoTest() throws Exception {

		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).conIsbn("12421").build();

		repositorioLibros.agregar(libro);

		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		try {
			// act
			blibliotecario.prestar(libro.getIsbn(), "Juan");
			fail();

		} catch (PrestamoException e) {
			// assert
			Assert.assertEquals("los libros palíndromos solo se pueden utilizar en la biblioteca", e.getMessage());
			Assert.assertNull(repositorioPrestamo.obtenerLibroPrestadoPorIsbn("1221"));
		}
	}

	@Test
	public void prestarLibroConUsuarioTest() throws Exception {

		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).build();
		repositorioLibros.agregar(libro);
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(libro.getIsbn(), "Juan");

		// assert
		Assert.assertTrue(blibliotecario.esPrestado(libro.getIsbn()));
		Assert.assertNotNull(repositorioPrestamo.obtener(libro.getIsbn()).getNombreUsuario());
		Assert.assertEquals("Juan", repositorioPrestamo.obtener(libro.getIsbn()).getNombreUsuario());

	}

	@Test
	public void prestarLibroConFechaEntregaTest() throws Exception {

		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).conIsbn("58749841647").build();
		repositorioLibros.agregar(libro);
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(libro.getIsbn(), "Juan");

		// assert
		Assert.assertTrue(blibliotecario.esPrestado(libro.getIsbn()));

		Calendar fechaPrestamo = Calendar.getInstance();
		asignarTiempoCero(fechaPrestamo);

		Calendar fechaObtenida = Calendar.getInstance();
		fechaObtenida.setTime(repositorioPrestamo.obtener(libro.getIsbn()).getFechaEntregaMaxima());
		asignarTiempoCero(fechaObtenida);

		Assert.assertEquals(CalificadorUtil.sumarDiasSinContarDomingos(fechaPrestamo, 15).getTime(),fechaObtenida.getTime());
	}

	private void asignarTiempoCero(Calendar fecha) {
		fecha.set(Calendar.HOUR, 0);
		fecha.set(Calendar.MILLISECOND, 0);
		fecha.set(Calendar.SECOND, 0);
		fecha.set(Calendar.MINUTE, 0);
	}
}

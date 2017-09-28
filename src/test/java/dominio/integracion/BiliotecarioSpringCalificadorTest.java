package dominio.integracion;

import static org.junit.Assert.fail;
import java.util.Calendar;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import dominio.Bibliotecario;
import dominio.Libro;
import dominio.excepcion.PrestamoException;
import rest.BibliotecarioApplicacion;
import testdatabuilder.LibroTestDataBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {BibliotecarioApplicacion.class})
@DataJpaTest
public class BiliotecarioSpringCalificadorTest {
	private static final String CRONICA_DE_UNA_MUERTA_ANUNCIADA = "Cronica de una muerta anunciada";
	
	@Autowired
	Bibliotecario bibliotecario;
	
	@Test
	public void prestarLibroTest() throws Exception {
		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).conIsbn("1234").build();
		bibliotecario.ingresarLibro(libro);
		// act
		bibliotecario.prestar(libro.getIsbn(), "Juan");
		// assert
		Assert.assertTrue(bibliotecario.esPrestado(libro.getIsbn()));
		Assert.assertNotNull(bibliotecario.obtenerLibro(libro.getIsbn()));

	}

	@Test
	public void prestarLibroNoDisponibleTest() throws Exception {
		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).build();
		bibliotecario.ingresarLibro(libro);
		// act
		bibliotecario.prestar(libro.getIsbn(), "Juan");
		try {
			bibliotecario.prestar(libro.getIsbn(), "Juan");
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
		bibliotecario.ingresarLibro(libro);
		try {
			// act
			bibliotecario.prestar(libro.getIsbn(), "Juan");
			fail();

		} catch (PrestamoException e) {
			// assert
			Assert.assertEquals("los libros palindromos solo se pueden utilizar en la biblioteca", e.getMessage());
			Assert.assertNull(bibliotecario.getRepositorioPrestamo().obtenerLibroPrestadoPorIsbn("1221"));
		}
	}

	@Test
	public void prestarLibroConUsuarioTest() throws Exception {
		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).build();
		bibliotecario.ingresarLibro(libro);
		// act
		bibliotecario.prestar(libro.getIsbn(), "Juan");
		// assert
		Assert.assertTrue(bibliotecario.esPrestado(libro.getIsbn()));
		Assert.assertNotNull(bibliotecario.obtenerPestamoPorIsbn(libro.getIsbn()).getNombreUsuario());
		Assert.assertEquals("Juan", bibliotecario.obtenerPestamoPorIsbn(libro.getIsbn()).getNombreUsuario());
	}

	@Test
	public void prestarLibroConFechaEntregaTest() throws Exception {
		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).conIsbn("58749841647").build();
		bibliotecario.ingresarLibro(libro);
		// act
		bibliotecario.prestar(libro.getIsbn(), "Juan");
		// assert
		Assert.assertTrue(bibliotecario.esPrestado(libro.getIsbn()));
		Calendar fechaPrestamo = Calendar.getInstance();
		asignarTiempoCero(fechaPrestamo);
		Calendar fechaObtenida = Calendar.getInstance();
		fechaObtenida.setTime(bibliotecario.obtenerPestamoPorIsbn(libro.getIsbn()).getFechaEntregaMaxima());
		asignarTiempoCero(fechaObtenida);
		Assert.assertEquals(CalificadorUtil.sumarDiasSinContarDomingos(fechaPrestamo, 15).getTime(),fechaObtenida.getTime());
	}

	private void asignarTiempoCero(Calendar fecha) {
		fecha.set(Calendar.HOUR, 0);
		fecha.set(Calendar.MILLISECOND, 0);
		fecha.set(Calendar.SECOND, 0);
		fecha.set(Calendar.MINUTE, 0);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BibliotecarioApplicacion.class, args);
	}
}

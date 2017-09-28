package dominio.unitaria;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import dominio.Libro;
import dominio.Prestamo;
import dominio.ReglaPosibilidadPrestamo;
import dominio.excepcion.PrestamoException;
import dominio.reglas.ReglaNoPrestarDomingo;
import testdatabuilder.LibroTestDataBuilder;

public class ReglaNoPrestarDomingoTest {
	
	@Test
	public void verificarPrestaromingo() throws java.text.ParseException{
		ReglaPosibilidadPrestamo reglaNoPrestarDomingo = new ReglaNoPrestarDomingo();
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatoDelTexto2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatoDelTexto3 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatoDelTexto4 = new SimpleDateFormat("yyyy-MM-dd");
		String strFecha1 = "2017-08-06";
		String strFecha2 = "2017-09-17";
		String strFecha3 ="2018-01-07"; 
		String strFecha4 = "2017-09-10";
		Date fecha1 = null;
		Date fecha2 = null;
		Date fecha3 = null;
		Date fecha4 = null;
		try {
			fecha1 = formatoDelTexto.parse(strFecha1);
			fecha2 = formatoDelTexto2.parse(strFecha2);
			fecha3 = formatoDelTexto3.parse(strFecha3);
			fecha4 = formatoDelTexto4.parse(strFecha4);
		} catch (ParseException ex) {
			ex.printStackTrace();	
		}
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
		Libro libro = libroTestDataBuilder.build(); 	
		Prestamo prestamo1 = new Prestamo(fecha1, libro, null, "Nombre de usuario");
		Prestamo prestamo2 = new Prestamo(fecha2, libro, null, "Nombre de usuario");
		Prestamo prestamo3 = new Prestamo(fecha3, libro, null, "Nombre de usuario");
		Prestamo prestamo4 = new Prestamo(fecha4, libro, null, "Nombre de usuario");	
		
		Prestamo[] prestamos = {prestamo1, prestamo2, prestamo3, prestamo4};
		
		for(int i =0; i<4; ++i){
			try {
				reglaNoPrestarDomingo.verificarPosibilidadDePrestamo(prestamos[i]);
			} catch (PrestamoException e) {
				Assert.assertEquals(ReglaNoPrestarDomingo.MSJ_NO_SE_PRESTA_DOMINGO, e.getMessage());
			}
		}
	}
}

package dominio.unitaria;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import dominio.util.Utilidad;

public class UtilidadTest {
	private static final String ISBN_PALINDROMO1="123AA321";
	private static final String ISBN_PALINDROMO2="1";
	private static final String ISBN_NO_PALINDROMO1="1039A";
	private static final String ISBN_NO_PALINDROMO2="123";
	
	@Test
	public void verificarPalindromoTest() {
		assertTrue(Utilidad.verificarPalindromo(ISBN_PALINDROMO1));	
		assertTrue(Utilidad.verificarPalindromo(ISBN_PALINDROMO2));	
		assertFalse(Utilidad.verificarPalindromo(ISBN_NO_PALINDROMO1));
		assertFalse(Utilidad.verificarPalindromo(ISBN_NO_PALINDROMO2));
	}
	@Test
	public void sumarIntCadenaTest(){
		
		assertEquals(12, Utilidad.sumarIntCadena(ISBN_PALINDROMO1));
		assertEquals(1, Utilidad.sumarIntCadena(ISBN_PALINDROMO2));
		assertEquals(13, Utilidad.sumarIntCadena(ISBN_NO_PALINDROMO1));
		assertEquals(6, Utilidad.sumarIntCadena(ISBN_NO_PALINDROMO2));
	}
	@Test
	public void sumar15DiasSinDomingosTest() throws java.text.ParseException{
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatoDelTexto2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatoDelTexto3 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatoDelTexto4 = new SimpleDateFormat("yyyy-MM-dd");
		String strFecha1 = "2017-09-15";
		String strFecha2 = "2017-09-03";
		String strFecha3 ="2017-05-24"; 
		String strFecha4 = "2017-05-26";
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
		Date fechaLimite1 = Utilidad.sumar15DiasSinDomingos(fecha1);
		Date fechaLimite2 = Utilidad.sumar15DiasSinDomingos(fecha2);
		Date fechaLimite3 = Utilidad.sumar15DiasSinDomingos(fecha3);
		Date fechaLimite4 = Utilidad.sumar15DiasSinDomingos(fecha4);
		Calendar calen1 = Utilidad.dateToCalendar(fechaLimite1);
		Calendar calen2 = Utilidad.dateToCalendar(fechaLimite2);
		Calendar calen3 = Utilidad.dateToCalendar(fechaLimite3);
		Calendar calen4 = Utilidad.dateToCalendar(fechaLimite4);
		assertEquals(2, calen1.get(Calendar.DAY_OF_WEEK));  //Debe ser 2
		assertEquals(4, calen2.get(Calendar.DAY_OF_WEEK));  //Debe ser 4
		assertEquals(6, calen3.get(Calendar.DAY_OF_WEEK));  
		assertEquals(2, calen4.get(Calendar.DAY_OF_WEEK));  
	}
}



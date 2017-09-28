package dominio.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Utilidad {
	private Utilidad(){}
	private static final int OPCION_DIA = 5;
	public static final int DIAS_DE_PRESTAMO_RESTRINGIDO = 16; //16	
	
	public static String invertirCadena(String cadena){
		StringBuilder sBCadena = new StringBuilder();
		sBCadena.append(cadena);
		sBCadena = sBCadena.reverse();		
		return sBCadena.toString();
	}	
	public static boolean verificarPalindromo(String cadenaTxt){
		return cadenaTxt.equals(invertirCadena(cadenaTxt));
	}	
	public static int sumarIntCadena(String cadena){
		int suma=0;
		for(int i=0; i<cadena.length(); i++){	
			char subCadena = cadena.charAt(i);
			if(Character.isDigit(subCadena)){
				int num1=Integer.parseInt(String.valueOf(subCadena)); 
				suma = suma+num1;
			}
		}		
		return suma;
	}
	public static void imprimir(String mensaje){
		BasicConfigurator.configure();
		Logger log = Logger.getLogger("  Aplicacion Biblioteca, Mensaje -------------------------------------------------------");		
		log.info(mensaje);		
	}
	public static Date sumar15DiasSinDomingos(Date fecha){	
		Date fecha1 = fecha;
		Calendar calen2 = dateToCalendar(fecha1);
		if(calen2.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			fecha1 = variarFecha(fecha1,OPCION_DIA, Calendar.SUNDAY);
			
		}
		fecha1 = variarFecha(fecha1,OPCION_DIA, DIAS_DE_PRESTAMO_RESTRINGIDO);		
		Calendar calen = dateToCalendar(fecha1);
		if(calen.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			fecha1 = variarFecha(fecha1,OPCION_DIA, 1);
		}
		return fecha1;

	}
	public static Date variarFecha(Date fecha, int campo, int valor){
	      if (valor==0) return fecha;
	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(fecha); 
	      calendar.add(campo, valor); 
	      return calendar.getTime(); 
	}
	public static Calendar dateToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
}
















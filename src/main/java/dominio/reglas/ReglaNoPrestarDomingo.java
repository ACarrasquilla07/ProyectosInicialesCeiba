package dominio.reglas;

import java.util.Calendar;

import dominio.Prestamo;
import dominio.ReglaPosibilidadPrestamo;
import dominio.excepcion.PrestamoException;
import dominio.util.Utilidad;

public class ReglaNoPrestarDomingo implements ReglaPosibilidadPrestamo{
	public static final String MSJ_NO_SE_PRESTA_DOMINGO = "no se puede prestar los dias festivos y no se efectuara el prestamo";

	@Override
	public Prestamo verificarPosibilidadDePrestamo(Prestamo prestamo) {
		Calendar fechaSolic = Utilidad.dateToCalendar(prestamo.getFechaSolicitud());
		if(fechaSolic.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){			
			throw new PrestamoException(MSJ_NO_SE_PRESTA_DOMINGO);
		}
		return prestamo;
	}
}

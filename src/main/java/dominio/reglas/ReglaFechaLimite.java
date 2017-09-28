package dominio.reglas;

import java.util.Date;
import dominio.Prestamo;
import dominio.ReglaPosibilidadPrestamo;
import dominio.util.Utilidad;

public class ReglaFechaLimite implements ReglaPosibilidadPrestamo {
	public static final int SUMA_DE_NUM_ISBN_MAX = 30;
	private static final Object MSJ_FECHA_LIM = "La fecha limite de entrega es: ";

	@Override
	public Prestamo verificarPosibilidadDePrestamo(Prestamo prestamo) {
		Date fechaEntregaMax = darFechaLimite(prestamo.getLibro().getIsbn(), prestamo.getFechaSolicitud());
		prestamo.setFechaEntregaMaxima(fechaEntregaMax);
		return prestamo;
	}

	public Date darFechaLimite(String isbn, Date fechaSolicitud) {
		Date fechaEntregaMaxima = null;
		if (Utilidad.sumarIntCadena(isbn) > SUMA_DE_NUM_ISBN_MAX) {
			fechaEntregaMaxima = Utilidad.sumar15DiasSinDomingos(fechaSolicitud);
			StringBuilder sBufferMsj = new StringBuilder();
			sBufferMsj.append(MSJ_FECHA_LIM).append(fechaEntregaMaxima.toString());
			Utilidad.imprimir(sBufferMsj.toString());
		}
		return fechaEntregaMaxima;
	}
}

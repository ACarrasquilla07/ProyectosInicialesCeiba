package dominio.reglas;

import dominio.Prestamo;
import dominio.ReglaPosibilidadPrestamo;
import dominio.excepcion.PrestamoException;
import dominio.util.Utilidad;

public class ReglaPalindromo implements ReglaPosibilidadPrestamo {
	public static final String MSJ_LIBRO_RESTRINGIDO = "los libros palíndromos solo se pueden utilizar en la biblioteca";

	@Override
	public Prestamo verificarPosibilidadDePrestamo(Prestamo prestamo) {
		if (Utilidad.verificarPalindromo(prestamo.getLibro().getIsbn())) {
			throw new PrestamoException(MSJ_LIBRO_RESTRINGIDO);
		}
		return prestamo;
	}
}

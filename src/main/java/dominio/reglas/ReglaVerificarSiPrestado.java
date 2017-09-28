package dominio.reglas;

import dominio.Prestamo;
import dominio.ReglaPosibilidadPrestamo;
import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioPrestamo;

public class ReglaVerificarSiPrestado implements ReglaPosibilidadPrestamo{
	private RepositorioPrestamo repositorioPrestamo;
	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
	
	public ReglaVerificarSiPrestado(RepositorioPrestamo repositorioPrestamo){
		this.repositorioPrestamo = repositorioPrestamo;
	}

	@Override
	public Prestamo verificarPosibilidadDePrestamo(Prestamo prestamo) {
		if(null != repositorioPrestamo.obtenerLibroPrestadoPorIsbn(prestamo.getLibro().getIsbn())){
			throw new PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
		}
		return prestamo;
	}
}

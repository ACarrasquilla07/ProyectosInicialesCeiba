package dominio;

import java.util.Date;

public class Prestamo {

	private Date fechaSolicitud;
	private Libro libro;
	private Date fechaEntregaMaxima;
	private String nombreUsuario;
	private boolean valido;

	public Prestamo(Libro libro) {
		this.fechaSolicitud = new Date();
		this.libro = libro;
	}

	public Prestamo(Date fechaSolicitud, Libro libro, Date fechaEntregaMaxima, String nombreUsuario) {
		this.fechaSolicitud = fechaSolicitud;
		this.libro = libro;
		this.fechaEntregaMaxima = fechaEntregaMaxima;
		this.nombreUsuario = nombreUsuario;
		this.valido = true;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public Libro getLibro() {
		return libro;
	}

	public Date getFechaEntregaMaxima() {
		return fechaEntregaMaxima;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

	public void setFechaEntregaMaxima(Date fechaEntregaMaxima) {
		this.fechaEntregaMaxima = fechaEntregaMaxima;
	}

}

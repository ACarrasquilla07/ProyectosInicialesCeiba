package rest.dto;

public class PeticionPrestamo {
	private String isbn;
	private String nombreUsuario;
	
	public PeticionPrestamo() {}

	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
}

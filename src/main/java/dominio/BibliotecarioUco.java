package dominio;

import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

public class BibliotecarioUco extends Bibliotecario {

	public BibliotecarioUco(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
		super(repositorioLibro, repositorioPrestamo);
	}
	
	@Override
	public void prestar(String isbn) {
		System.out.println("Hola vamos a prestar");
		super.prestar(isbn);
	}

}

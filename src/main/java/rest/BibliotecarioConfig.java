package rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dominio.Bibliotecario;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

@Configuration
public class BibliotecarioConfig {
	
	@Bean
	public Bibliotecario crearBibliorecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {		
		return new Bibliotecario(repositorioLibro , repositorioPrestamo);
	}
}

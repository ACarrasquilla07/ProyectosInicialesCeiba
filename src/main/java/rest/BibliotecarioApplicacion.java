package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"persistencia.repositorio","rest"})
@EntityScan("persistencia.entitad")
public class BibliotecarioApplicacion {
	public static void main(String[] args) {
		SpringApplication.run(BibliotecarioApplicacion.class, args);
	}
}

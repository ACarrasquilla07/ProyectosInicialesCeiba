package com.bibliotecario.bibliotecario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dominio.Bibliotecario;
import dominio.repositorio.RepositorioPrestamo;

@RestController
public class BibliotecarioRest {

	@Autowired
	Bibliotecario bibliotecario;
	
	@Autowired
	RepositorioPrestamo repositorioPrestamo;
	
	@RequestMapping("/holamundo")
	public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		bibliotecario.prestar("1234");
		return "Hola " + name;
	}

}

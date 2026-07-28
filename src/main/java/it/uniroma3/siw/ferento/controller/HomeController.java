package it.uniroma3.siw.ferento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	// GET "/" restituisce il nome del template "index" (index.html),
	// che Thymeleaf risolve e mostra come home page pubblica.
	@GetMapping("/")
	public String getHome() {
		return "index";
	}
}
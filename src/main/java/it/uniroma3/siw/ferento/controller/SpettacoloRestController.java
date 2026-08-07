package it.uniroma3.siw.ferento.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.ferento.dto.SpettacoloRestDTO;
import it.uniroma3.siw.ferento.service.SpettacoloService;

/*
 * @RestController: a differenza di @Controller, i metodi NON restituiscono
 * il nome di una pagina, ma DATI, che Spring serializza automaticamente in
 * JSON. E' l'endpoint che l'app React consumera' via axios.
 */
@RestController
@RequestMapping("/rest")
public class SpettacoloRestController {

	private final SpettacoloService spettacoloService;

	public SpettacoloRestController(SpettacoloService spettacoloService) {
		this.spettacoloService = spettacoloService;
	}

	// GET /rest/spettacoli : lista degli spettacoli in JSON.
	// Convertiamo ogni entita' in un DTO prima di restituirla.
	@GetMapping("/spettacoli")
	public List<SpettacoloRestDTO> getSpettacoli() {
		return this.spettacoloService.findAll().stream()
			.map(SpettacoloRestDTO::new)
			.toList();
	}
}
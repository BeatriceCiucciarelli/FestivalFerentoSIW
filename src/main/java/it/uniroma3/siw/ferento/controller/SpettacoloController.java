package it.uniroma3.siw.ferento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.ferento.service.SpettacoloService;

@Controller
public class SpettacoloController {

	private final SpettacoloService spettacoloService;

	public SpettacoloController(SpettacoloService spettacoloService) {
		this.spettacoloService = spettacoloService;
	}

	// GET /spettacoli : elenco pubblico del cartellone.
	// Mette la lista nel Model con la chiave "spettacoli", che il template
	// usera' per iterare, e restituisce il template spettacoli/lista.
	@GetMapping("/spettacoli")
	public String getSpettacoli(Model model) {
		model.addAttribute("spettacoli", this.spettacoloService.findAll());
		return "spettacoli/lista";
	}
}
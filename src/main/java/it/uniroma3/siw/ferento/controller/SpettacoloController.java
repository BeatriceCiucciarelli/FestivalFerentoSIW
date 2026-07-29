package it.uniroma3.siw.ferento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.ferento.service.SettoreService;
import it.uniroma3.siw.ferento.service.SpettacoloService;

@Controller
public class SpettacoloController {

	private final SpettacoloService spettacoloService;
	private final SettoreService settoreService;

	public SpettacoloController(SpettacoloService spettacoloService, SettoreService settoreService) {
		this.spettacoloService = spettacoloService;
		this.settoreService = settoreService;
	}

	// GET /spettacoli : elenco pubblico del cartellone.
	@GetMapping("/spettacoli")
	public String getSpettacoli(Model model) {
		model.addAttribute("spettacoli", this.spettacoloService.findAll());
		return "spettacoli/lista";
	}

	// GET /spettacoli/{id} : dettaglio di un singolo spettacolo.
	// @PathVariable lega la parte variabile dell'URL (l'id) al parametro.
	// Se l'id non esiste, il service lancia l'eccezione mappata a 404.
	@GetMapping("/spettacoli/{id}")
	public String getSpettacolo(@PathVariable("id") Long id, Model model) {
		model.addAttribute("spettacolo", this.spettacoloService.findById(id));
		model.addAttribute("settori", this.settoreService.findAll());
		return "spettacoli/dettaglio";
	}
}
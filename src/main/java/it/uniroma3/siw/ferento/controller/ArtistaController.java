package it.uniroma3.siw.ferento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.ferento.service.ArtistaService;

@Controller
public class ArtistaController {

	private final ArtistaService artistaService;

	public ArtistaController(ArtistaService artistaService) {
		this.artistaService = artistaService;
	}

	// GET /artisti : elenco pubblico degli artisti.
	@GetMapping("/artisti")
	public String getArtisti(Model model) {
		model.addAttribute("artisti", this.artistaService.findAll());
		return "artisti/lista";
	}

	// GET /artisti/{id} : dettaglio di un artista, con i suoi spettacoli.
	@GetMapping("/artisti/{id}")
	public String getArtista(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artista", this.artistaService.findById(id));
		return "artisti/dettaglio";
	}
}
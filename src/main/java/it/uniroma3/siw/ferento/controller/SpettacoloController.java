package it.uniroma3.siw.ferento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.ferento.model.Spettacolo;
import it.uniroma3.siw.ferento.service.RecensioneService;
import it.uniroma3.siw.ferento.service.SettoreService;
import it.uniroma3.siw.ferento.service.SpettacoloService;

@Controller
public class SpettacoloController {

	private final SpettacoloService spettacoloService;
	private final SettoreService settoreService;
	private final RecensioneService recensioneService;

	public SpettacoloController(SpettacoloService spettacoloService, SettoreService settoreService,
			RecensioneService recensioneService) {
		this.spettacoloService = spettacoloService;
		this.settoreService = settoreService;
		this.recensioneService = recensioneService;
	}

	// GET /spettacoli : elenco pubblico del cartellone.
	@GetMapping("/spettacoli")
	public String getSpettacoli(Model model) {
		model.addAttribute("spettacoli", this.spettacoloService.findAll());
		return "spettacoli/lista";
	}

	// GET /spettacoli/{id} : dettaglio di un singolo spettacolo,
	// con settori, recensioni e voto medio.
	@GetMapping("/spettacoli/{id}")
	public String getSpettacolo(@PathVariable("id") Long id, Model model) {
		Spettacolo spettacolo = this.spettacoloService.findById(id);

		double votoMedio = this.recensioneService.votoMedio(spettacolo);

		model.addAttribute("spettacolo", spettacolo);
		model.addAttribute("settori", this.settoreService.findAll());
		model.addAttribute("recensioni", this.recensioneService.findBySpettacolo(spettacolo));
		model.addAttribute("votoMedio", votoMedio);
		// Valore arrotondato all'intero piu' vicino, per disegnare le stelle.
		model.addAttribute("votoMedioStelle", (int) Math.round(votoMedio));

		return "spettacoli/dettaglio";
	}
}
package it.uniroma3.siw.ferento.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.ferento.model.Spettacolo;
import it.uniroma3.siw.ferento.model.Utente;
import it.uniroma3.siw.ferento.service.RecensioneService;
import it.uniroma3.siw.ferento.service.SettoreService;
import it.uniroma3.siw.ferento.service.SpettacoloService;
import it.uniroma3.siw.ferento.service.UtenteService;

@Controller
public class SpettacoloController {

	private final SpettacoloService spettacoloService;
	private final SettoreService settoreService;
	private final RecensioneService recensioneService;
	private final UtenteService utenteService;

	public SpettacoloController(SpettacoloService spettacoloService, SettoreService settoreService,
			RecensioneService recensioneService, UtenteService utenteService) {
		this.spettacoloService = spettacoloService;
		this.settoreService = settoreService;
		this.recensioneService = recensioneService;
		this.utenteService = utenteService;
	}

	// GET /spettacoli : elenco pubblico del cartellone.
	@GetMapping("/spettacoli")
	public String getSpettacoli(Model model) {
		model.addAttribute("spettacoli", this.spettacoloService.findAll());
		return "spettacoli/lista";
	}

	// GET /spettacoli/{id} : dettaglio con settori, recensioni e voto medio.
	// Principal e' null se il visitatore non e' autenticato.
	@GetMapping("/spettacoli/{id}")
	public String getSpettacolo(@PathVariable("id") Long id, Model model, Principal principal) {
		Spettacolo spettacolo = this.spettacoloService.findById(id);

		double votoMedio = this.recensioneService.votoMedio(spettacolo);

		model.addAttribute("spettacolo", spettacolo);
		model.addAttribute("settori", this.settoreService.findAll());
		model.addAttribute("recensioni", this.recensioneService.findBySpettacolo(spettacolo));
		model.addAttribute("votoMedio", votoMedio);
		model.addAttribute("votoMedioStelle", (int) Math.round(votoMedio));

		// Se il visitatore e' loggato, recuperiamo la sua eventuale
		// recensione per questo spettacolo (per mostrare "scrivi" oppure
		// "hai gia' recensito").
		if (principal != null) {
			Utente utente = this.utenteService.getByUsername(principal.getName());
			model.addAttribute("recensioneUtente",
				this.recensioneService.findRecensioneUtente(utente, spettacolo));
		}

		return "spettacoli/dettaglio";
	}
}
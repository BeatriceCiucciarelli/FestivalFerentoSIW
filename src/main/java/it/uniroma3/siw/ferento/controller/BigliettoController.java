package it.uniroma3.siw.ferento.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.ferento.exception.DisponibilitaInsufficienteException;
import it.uniroma3.siw.ferento.model.Settore;
import it.uniroma3.siw.ferento.model.Spettacolo;
import it.uniroma3.siw.ferento.model.Utente;
import it.uniroma3.siw.ferento.service.BigliettoService;
import it.uniroma3.siw.ferento.service.SettoreService;
import it.uniroma3.siw.ferento.service.SpettacoloService;
import it.uniroma3.siw.ferento.service.UtenteService;

@Controller
@RequestMapping("/biglietti")
public class BigliettoController {

	private final BigliettoService bigliettoService;
	private final SpettacoloService spettacoloService;
	private final SettoreService settoreService;
	private final UtenteService utenteService;

	public BigliettoController(BigliettoService bigliettoService, SpettacoloService spettacoloService,
			SettoreService settoreService, UtenteService utenteService) {
		this.bigliettoService = bigliettoService;
		this.spettacoloService = spettacoloService;
		this.settoreService = settoreService;
		this.utenteService = utenteService;
	}

	// POST /biglietti : effettua l'acquisto.
	@PostMapping
	public String acquista(@RequestParam("spettacoloId") Long spettacoloId,
			@RequestParam("settoreId") Long settoreId,
			@RequestParam("quantita") int quantita, Principal principal) {

		Spettacolo spettacolo = this.spettacoloService.findById(spettacoloId);
		Settore settore = this.settoreService.findById(settoreId);
		Utente utente = this.utenteService.getByUsername(principal.getName());

		try {
			this.bigliettoService.acquista(spettacolo, settore, quantita, utente);
		} catch (DisponibilitaInsufficienteException e) {
			// Torna al dettaglio segnalando l'errore (via query param).
			return "redirect:/spettacoli/" + spettacoloId + "?erroreAcquisto";
		}

		// Acquisto riuscito: mostra i biglietti dell'utente.
		return "redirect:/biglietti/miei";
	}

	// GET /biglietti/miei : elenco dei biglietti dell'utente loggato.
	@GetMapping("/miei")
	public String mieiBiglietti(Model model, Principal principal) {
		Utente utente = this.utenteService.getByUsername(principal.getName());
		model.addAttribute("biglietti", this.bigliettoService.findByUtente(utente));
		return "biglietti/miei";
	}
}
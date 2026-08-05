package it.uniroma3.siw.ferento.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.ferento.model.Recensione;
import it.uniroma3.siw.ferento.model.Spettacolo;
import it.uniroma3.siw.ferento.model.Utente;
import it.uniroma3.siw.ferento.service.RecensioneService;
import it.uniroma3.siw.ferento.service.SpettacoloService;
import it.uniroma3.siw.ferento.service.UtenteService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/recensioni")
public class RecensioneController {

	private final RecensioneService recensioneService;
	private final SpettacoloService spettacoloService;
	private final UtenteService utenteService;

	public RecensioneController(RecensioneService recensioneService, SpettacoloService spettacoloService,
			UtenteService utenteService) {
		this.recensioneService = recensioneService;
		this.spettacoloService = spettacoloService;
		this.utenteService = utenteService;
	}

	// GET /recensioni/nuova?spettacoloId=X : mostra il form di scrittura.
	@GetMapping("/nuova")
	public String nuovaRecensione(@RequestParam("spettacoloId") Long spettacoloId, Model model, Principal principal) {
		Spettacolo spettacolo = this.spettacoloService.findById(spettacoloId);
		Utente utente = this.utenteService.getByUsername(principal.getName());

		// Se l'utente ha gia' recensito, non ha senso mostrare il form:
		// lo riportiamo al dettaglio.
		if (this.recensioneService.findRecensioneUtente(utente, spettacolo) != null) {
			return "redirect:/spettacoli/" + spettacoloId;
		}

		model.addAttribute("recensione", new Recensione());
		model.addAttribute("spettacolo", spettacolo);
		return "recensioni/form";
	}

	// POST /recensioni : salva la nuova recensione.
	@PostMapping
	public String salvaRecensione(@RequestParam("spettacoloId") Long spettacoloId,
			@Valid @ModelAttribute("recensione") Recensione recensione,
			BindingResult bindingResult, Model model, Principal principal) {

		Spettacolo spettacolo = this.spettacoloService.findById(spettacoloId);

		// Se voto o testo non sono validi, torniamo al form mostrando gli errori.
		if (bindingResult.hasErrors()) {
			model.addAttribute("spettacolo", spettacolo);
			return "recensioni/form";
		}

		Utente utente = this.utenteService.getByUsername(principal.getName());
		this.recensioneService.crea(recensione, utente, spettacolo);

		// Post-Redirect-Get verso il dettaglio dello spettacolo.
		return "redirect:/spettacoli/" + spettacoloId;
	}
}
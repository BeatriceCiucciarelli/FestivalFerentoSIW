package it.uniroma3.siw.ferento.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

	// GET /recensioni/nuova?spettacoloId=X : form di scrittura.
	@GetMapping("/nuova")
	public String nuovaRecensione(@RequestParam("spettacoloId") Long spettacoloId, Model model, Principal principal) {
		Spettacolo spettacolo = this.spettacoloService.findById(spettacoloId);
		Utente utente = this.utenteService.getByUsername(principal.getName());

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

		if (bindingResult.hasErrors()) {
			model.addAttribute("spettacolo", spettacolo);
			return "recensioni/form";
		}

		Utente utente = this.utenteService.getByUsername(principal.getName());
		this.recensioneService.crea(recensione, utente, spettacolo);

		return "redirect:/spettacoli/" + spettacoloId;
	}

	// GET /recensioni/{id}/modifica : form di modifica, precompilato.
	// getRecensionePropria verifica che la recensione sia dell'utente
	// loggato: chi non e' l'autore riceve un 403 e non vede nemmeno il form.
	@GetMapping("/{id}/modifica")
	public String modificaRecensione(@PathVariable("id") Long id, Model model, Principal principal) {
		Utente utente = this.utenteService.getByUsername(principal.getName());
		Recensione recensione = this.recensioneService.getRecensionePropria(id, utente);

		model.addAttribute("recensione", recensione);
		model.addAttribute("spettacolo", recensione.getSpettacolo());
		return "recensioni/form";
	}

	// POST /recensioni/{id} : salva le modifiche.
	// La verifica di ownership vera avviene nel service (aggiorna).
	@PostMapping("/{id}")
	public String aggiornaRecensione(@PathVariable("id") Long id,
			@Valid @ModelAttribute("recensione") Recensione recensione,
			BindingResult bindingResult, Model model, Principal principal) {

		Utente utente = this.utenteService.getByUsername(principal.getName());
		// Carica (e verifica proprieta') per conoscere lo spettacolo, utile
		// sia in caso di errori di validazione sia per il redirect finale.
		Recensione esistente = this.recensioneService.getRecensionePropria(id, utente);

		if (bindingResult.hasErrors()) {
			model.addAttribute("spettacolo", esistente.getSpettacolo());
			return "recensioni/form";
		}

		this.recensioneService.aggiorna(id, recensione, utente);
		return "redirect:/spettacoli/" + esistente.getSpettacolo().getId();
	}
}
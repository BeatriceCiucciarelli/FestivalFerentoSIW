package it.uniroma3.siw.ferento.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.ferento.model.Artista;
import it.uniroma3.siw.ferento.model.Spettacolo;
import it.uniroma3.siw.ferento.model.Utente;
import it.uniroma3.siw.ferento.service.ArtistaService;
import it.uniroma3.siw.ferento.service.BigliettoService;
import it.uniroma3.siw.ferento.service.RecensioneService;
import it.uniroma3.siw.ferento.service.SettoreService;
import it.uniroma3.siw.ferento.service.SpettacoloService;
import it.uniroma3.siw.ferento.service.UtenteService;
import jakarta.validation.Valid;

@Controller
public class SpettacoloController {

	private final SpettacoloService spettacoloService;
	private final SettoreService settoreService;
	private final RecensioneService recensioneService;
	private final UtenteService utenteService;
	private final ArtistaService artistaService;
	private final BigliettoService bigliettoService;

	public SpettacoloController(SpettacoloService spettacoloService, SettoreService settoreService,
			RecensioneService recensioneService, UtenteService utenteService, ArtistaService artistaService,
			BigliettoService bigliettoService) {
		this.spettacoloService = spettacoloService;
		this.settoreService = settoreService;
		this.recensioneService = recensioneService;
		this.utenteService = utenteService;
		this.artistaService = artistaService;
		this.bigliettoService = bigliettoService;
	}

	// ---------- Parte pubblica ----------

	@GetMapping("/spettacoli")
	public String getSpettacoli(Model model) {
		model.addAttribute("spettacoli", this.spettacoloService.findAll());
		return "spettacoli/lista";
	}

	@GetMapping("/spettacoli/{id}")
	public String getSpettacolo(@PathVariable("id") Long id, Model model, Principal principal) {
		Spettacolo spettacolo = this.spettacoloService.findById(id);
		double votoMedio = this.recensioneService.votoMedio(spettacolo);

		model.addAttribute("spettacolo", spettacolo);
		model.addAttribute("disponibilita",
			this.bigliettoService.disponibilitaPerSpettacolo(spettacolo, this.settoreService.findAll()));
		model.addAttribute("recensioni", this.recensioneService.findBySpettacolo(spettacolo));
		model.addAttribute("votoMedio", votoMedio);
		model.addAttribute("votoMedioStelle", (int) Math.round(votoMedio));

		if (principal != null) {
			Utente utente = this.utenteService.getByUsername(principal.getName());
			model.addAttribute("recensioneUtente",
				this.recensioneService.findRecensioneUtente(utente, spettacolo));
		}

		return "spettacoli/dettaglio";
	}

	// ---------- Parte amministrativa ----------

	// Aggiunge al model le liste che servono al form (artisti e generi).
	private void popolaOpzioniForm(Model model) {
		model.addAttribute("artisti", this.artistaService.findAll());
		model.addAttribute("generi", Spettacolo.Genere.values());
	}

	@GetMapping("/admin/spettacoli")
	public String adminSpettacoli(Model model) {
		model.addAttribute("spettacoli", this.spettacoloService.findAll());
		return "admin/spettacoli/lista";
	}

	@GetMapping("/admin/spettacoli/nuovo")
	public String formNuovoSpettacolo(Model model) {
		model.addAttribute("spettacolo", new Spettacolo());
		this.popolaOpzioniForm(model);
		return "admin/spettacoli/form";
	}

	@PostMapping("/admin/spettacoli")
	public String creaSpettacolo(@RequestParam("artistaId") Long artistaId,
			@Valid @ModelAttribute("spettacolo") Spettacolo spettacolo, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			this.popolaOpzioniForm(model);
			return "admin/spettacoli/form";
		}
		Artista artista = this.artistaService.findById(artistaId);
		Spettacolo salvato = this.spettacoloService.salva(spettacolo, artista);
		return "redirect:/spettacoli/" + salvato.getId();
	}

	@GetMapping("/admin/spettacoli/{id}/modifica")
	public String formModificaSpettacolo(@PathVariable("id") Long id, Model model) {
		model.addAttribute("spettacolo", this.spettacoloService.findById(id));
		this.popolaOpzioniForm(model);
		return "admin/spettacoli/form";
	}

	@PostMapping("/admin/spettacoli/{id}")
	public String aggiornaSpettacolo(@PathVariable("id") Long id, @RequestParam("artistaId") Long artistaId,
			@Valid @ModelAttribute("spettacolo") Spettacolo spettacoloForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			spettacoloForm.setId(id);
			this.popolaOpzioniForm(model);
			return "admin/spettacoli/form";
		}
		Artista artista = this.artistaService.findById(artistaId);
		Spettacolo aggiornato = this.spettacoloService.aggiorna(id, spettacoloForm, artista);
		return "redirect:/spettacoli/" + aggiornato.getId();
	}

	@PostMapping("/admin/spettacoli/{id}/elimina")
	public String eliminaSpettacolo(@PathVariable("id") Long id) {
		this.spettacoloService.elimina(id);
		return "redirect:/admin/spettacoli";
	}
}
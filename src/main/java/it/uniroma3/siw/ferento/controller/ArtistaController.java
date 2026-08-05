package it.uniroma3.siw.ferento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.ferento.model.Artista;
import it.uniroma3.siw.ferento.service.ArtistaService;
import jakarta.validation.Valid;

@Controller
public class ArtistaController {

	private final ArtistaService artistaService;

	public ArtistaController(ArtistaService artistaService) {
		this.artistaService = artistaService;
	}

	// ---------- Parte pubblica ----------

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

	// ---------- Parte amministrativa (sotto /admin, protetta) ----------

	// Elenco di gestione, con i pulsanti per creare e modificare.
	@GetMapping("/admin/artisti")
	public String adminArtisti(Model model) {
		model.addAttribute("artisti", this.artistaService.findAll());
		return "admin/artisti/lista";
	}

	// Form per un nuovo artista (oggetto vuoto).
	@GetMapping("/admin/artisti/nuovo")
	public String formNuovoArtista(Model model) {
		model.addAttribute("artista", new Artista());
		return "admin/artisti/form";
	}

	// Salvataggio del nuovo artista.
	@PostMapping("/admin/artisti")
	public String creaArtista(@Valid @ModelAttribute("artista") Artista artista, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "admin/artisti/form";
		}
		Artista salvato = this.artistaService.salva(artista);
		return "redirect:/artisti/" + salvato.getId();
	}

	// Form di modifica, precompilato con i dati dell'artista.
	@GetMapping("/admin/artisti/{id}/modifica")
	public String formModificaArtista(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artista", this.artistaService.findById(id));
		return "admin/artisti/form";
	}

	// Salvataggio delle modifiche.
	@PostMapping("/admin/artisti/{id}")
	public String aggiornaArtista(@PathVariable("id") Long id,
			@Valid @ModelAttribute("artista") Artista artistaForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// Reimpostiamo l'id cosi' il form sa che e' una modifica.
			artistaForm.setId(id);
			return "admin/artisti/form";
		}
		Artista aggiornato = this.artistaService.aggiorna(id, artistaForm);
		return "redirect:/artisti/" + aggiornato.getId();
	}
}
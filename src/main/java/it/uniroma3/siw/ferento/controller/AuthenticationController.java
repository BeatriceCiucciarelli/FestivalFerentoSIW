package it.uniroma3.siw.ferento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.ferento.exception.UsernameGiaRegistratoException;
import it.uniroma3.siw.ferento.model.Utente;
import it.uniroma3.siw.ferento.service.UtenteService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {

	private final UtenteService utenteService;

	public AuthenticationController(UtenteService utenteService) {
		this.utenteService = utenteService;
	}

	// Mostra la pagina di login. L'autenticazione vera e propria e'
	// gestita da Spring Security (POST /login intercettato dal filtro),
	// qui serviamo solo il template con il form.
	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}

	// Mostra il form di registrazione, passando al template un oggetto
	// Utente vuoto a cui il form si aggancia (th:object).
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("utente", new Utente());
		return "register";
	}

	// Elabora l'invio del form di registrazione.
	// @Valid attiva i vincoli Bean Validation sull'Utente; BindingResult
	// raccoglie gli errori. In caso di errori si torna al form.
	@PostMapping("/register")
	public String registraUtente(@Valid @ModelAttribute("utente") Utente utente, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "register";
		}

		try {
			this.utenteService.registraNuovoUtente(utente);
			// Post-Redirect-Get: dopo il salvataggio reindirizziamo alla
			// pagina di login (con un parametro per il messaggio di successo).
			return "redirect:/login?registrato";
		} catch (UsernameGiaRegistratoException e) {
			// L'username era gia' in uso: trasformiamo l'eccezione del
			// service in un errore sul campo "username", mostrato dal form.
			bindingResult.rejectValue("username", "utente.duplicate", "Questo username è già in uso");
			return "register";
		}
	}
}
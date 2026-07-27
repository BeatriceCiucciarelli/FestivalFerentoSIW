package it.uniroma3.siw.ferento.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.ferento.exception.UsernameGiaRegistratoException;
import it.uniroma3.siw.ferento.model.Utente;
import it.uniroma3.siw.ferento.repository.UtenteRepository;

@Service
public class UtenteService {

	// Dipendenze dichiarate come campi final e ricevute dal costruttore
	// (costruttore injection): lo stile preferito perche' rende esplicite
	// le dipendenze del service e ne facilita il test.
	private final UtenteRepository utenteRepository;
	private final PasswordEncoder passwordEncoder;

	public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
		this.utenteRepository = utenteRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/*
	 * Registra un nuovo utente.
	 *
	 * @Transactional (di scrittura): il metodo modifica lo stato del
	 * sistema. Il controllo di esistenza e il salvataggio avvengono nella
	 * stessa transazione, come un blocco unico e coerente.
	 *
	 * Passi:
	 *  1. se l'username e' gia' preso, interrompe lanciando l'eccezione;
	 *  2. cifra la password con BCrypt (non salviamo MAI la password in
	 *     chiaro);
	 *  3. assegna il ruolo "USER" di default (un nuovo iscritto non e' admin);
	 *  4. salva e restituisce l'utente persistito.
	 */
	@Transactional
	public Utente registraNuovoUtente(Utente utente) {
		if (this.utenteRepository.findByUsername(utente.getUsername()).isPresent()) {
			throw new UsernameGiaRegistratoException();
		}

		utente.setPassword(this.passwordEncoder.encode(utente.getPassword()));
		utente.setRuolo("USER");

		return this.utenteRepository.save(utente);
	}
}
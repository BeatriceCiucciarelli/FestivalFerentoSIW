package it.uniroma3.siw.ferento.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.ferento.exception.UsernameGiaRegistratoException;
import it.uniroma3.siw.ferento.model.Utente;
import it.uniroma3.siw.ferento.repository.UtenteRepository;

@Service
public class UtenteService {

	private final UtenteRepository utenteRepository;
	private final PasswordEncoder passwordEncoder;

	public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
		this.utenteRepository = utenteRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/*
	 * Registra un nuovo utente: controlla che l'username sia libero, cifra
	 * la password con BCrypt, assegna il ruolo USER e salva.
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

	/*
	 * Recupera l'Utente dato l'username. Usato per risalire all'utente
	 * loggato a partire dal Principal (che contiene solo l'username).
	 */
	@Transactional(readOnly = true)
	public Utente getByUsername(String username) {
		return this.utenteRepository.findByUsername(username)
			.orElseThrow(() -> new IllegalStateException("Utente non trovato: " + username));
	}
}
package it.uniroma3.siw.ferento.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.ferento.exception.AccessoNegatoException;
import it.uniroma3.siw.ferento.exception.RecensioneNonTrovataException;
import it.uniroma3.siw.ferento.model.Recensione;
import it.uniroma3.siw.ferento.model.Spettacolo;
import it.uniroma3.siw.ferento.model.Utente;
import it.uniroma3.siw.ferento.repository.RecensioneRepository;

@Service
public class RecensioneService {

	private final RecensioneRepository recensioneRepository;

	public RecensioneService(RecensioneRepository recensioneRepository) {
		this.recensioneRepository = recensioneRepository;
	}

	// Le recensioni di uno spettacolo, dalla piu' recente.
	@Transactional(readOnly = true)
	public List<Recensione> findBySpettacolo(Spettacolo spettacolo) {
		return this.recensioneRepository.findBySpettacoloOrderByIdDesc(spettacolo);
	}

	// Voto medio di uno spettacolo (0.0 se non ci sono recensioni).
	@Transactional(readOnly = true)
	public double votoMedio(Spettacolo spettacolo) {
		Double media = this.recensioneRepository.votoMedio(spettacolo);
		return media != null ? media : 0.0;
	}

	// La recensione dell'utente per questo spettacolo, oppure null.
	@Transactional(readOnly = true)
	public Recensione findRecensioneUtente(Utente utente, Spettacolo spettacolo) {
		return this.recensioneRepository.findByUtenteAndSpettacolo(utente, spettacolo).orElse(null);
	}

	// Una recensione dato l'id (404 se non esiste).
	@Transactional(readOnly = true)
	public Recensione findById(Long id) {
		return this.recensioneRepository.findById(id)
			.orElseThrow(() -> new RecensioneNonTrovataException());
	}

	/*
	 * Carica una recensione VERIFICANDO che appartenga all'utente indicato.
	 * Controllo di ownership centralizzato: se l'autore non coincide con
	 * l'utente corrente, lancia AccessoNegatoException (403).
	 * Usato sia per mostrare il form di modifica sia per salvare.
	 */
	@Transactional(readOnly = true)
	public Recensione getRecensionePropria(Long id, Utente utenteCorrente) {
		Recensione recensione = this.findById(id);
		if (!recensione.getUtente().equals(utenteCorrente)) {
			throw new AccessoNegatoException();
		}
		return recensione;
	}

	/*
	 * Crea una nuova recensione. L'associazione con utente e spettacolo
	 * avviene qui; un utente puo' recensire uno spettacolo una sola volta.
	 */
	@Transactional
	public Recensione crea(Recensione recensione, Utente utente, Spettacolo spettacolo) {
		if (this.recensioneRepository.findByUtenteAndSpettacolo(utente, spettacolo).isPresent()) {
			throw new IllegalStateException("Hai già recensito questo spettacolo");
		}
		recensione.setUtente(utente);
		recensione.setSpettacolo(spettacolo);
		return this.recensioneRepository.save(recensione);
	}

	/*
	 * Aggiorna una recensione esistente, ma solo se appartiene all'utente
	 * corrente (verifica di ownership tramite getRecensionePropria).
	 * Copiamo dai dati del form soltanto voto e testo: utente e spettacolo
	 * restano quelli originali e non sono modificabili.
	 */
	@Transactional
	public Recensione aggiorna(Long id, Recensione dati, Utente utenteCorrente) {
		Recensione esistente = this.getRecensionePropria(id, utenteCorrente);
		esistente.setVoto(dati.getVoto());
		esistente.setTesto(dati.getTesto());
		return this.recensioneRepository.save(esistente);
	}
}
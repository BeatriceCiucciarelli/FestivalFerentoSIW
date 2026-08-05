package it.uniroma3.siw.ferento.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	// La recensione dell'utente per questo spettacolo, oppure null se non
	// esiste. Usata dall'interfaccia per decidere cosa mostrare.
	@Transactional(readOnly = true)
	public Recensione findRecensioneUtente(Utente utente, Spettacolo spettacolo) {
		return this.recensioneRepository.findByUtenteAndSpettacolo(utente, spettacolo).orElse(null);
	}

	/*
	 * Crea una nuova recensione.
	 *
	 * L'associazione con l'utente e con lo spettacolo avviene QUI, nel
	 * service: il form fornisce solo voto e testo; l'utente arriva dalla
	 * sessione (non dal form) e lo spettacolo dal suo id.
	 *
	 * Regola di business: un utente puo' recensire uno spettacolo una sola
	 * volta. Se esiste gia' una sua recensione, l'operazione viene rifiutata.
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
}
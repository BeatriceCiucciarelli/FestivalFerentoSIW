package it.uniroma3.siw.ferento.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.ferento.dto.DisponibilitaSettore;
import it.uniroma3.siw.ferento.exception.DisponibilitaInsufficienteException;
import it.uniroma3.siw.ferento.model.Biglietto;
import it.uniroma3.siw.ferento.model.Settore;
import it.uniroma3.siw.ferento.model.Spettacolo;
import it.uniroma3.siw.ferento.model.Utente;
import it.uniroma3.siw.ferento.repository.BigliettoRepository;

@Service
public class BigliettoService {

	private final BigliettoRepository bigliettoRepository;

	public BigliettoService(BigliettoRepository bigliettoRepository) {
		this.bigliettoRepository = bigliettoRepository;
	}

	// Posti gia' venduti per uno spettacolo in un settore.
	@Transactional(readOnly = true)
	public int postiVenduti(Spettacolo spettacolo, Settore settore) {
		Long venduti = this.bigliettoRepository.postiVenduti(spettacolo, settore);
		return venduti != null ? venduti.intValue() : 0;
	}

	// Posti ancora disponibili = capienza - venduti.
	@Transactional(readOnly = true)
	public int postiDisponibili(Spettacolo spettacolo, Settore settore) {
		return settore.getCapienza() - this.postiVenduti(spettacolo, settore);
	}

	// Lista "settore + posti disponibili" per uno spettacolo (DTO per la vista).
	@Transactional(readOnly = true)
	public List<DisponibilitaSettore> disponibilitaPerSpettacolo(Spettacolo spettacolo, List<Settore> settori) {
		List<DisponibilitaSettore> risultato = new ArrayList<>();
		for (Settore settore : settori) {
			int disponibili = this.postiDisponibili(spettacolo, settore);
			risultato.add(new DisponibilitaSettore(settore, disponibili));
		}
		return risultato;
	}

	/*
	 * Acquisto (simulato, senza pagamento reale).
	 *
	 * @Transactional di scrittura: il controllo di disponibilita' e la
	 * creazione del biglietto avvengono nella stessa transazione.
	 *
	 * Controlli di business (qui, nel service):
	 *  - la quantita' deve essere almeno 1;
	 *  - non si possono acquistare piu' posti di quelli disponibili.
	 * In caso contrario lancia DisponibilitaInsufficienteException.
	 *
	 * L'utente arriva dalla sessione (non dal form); la data d'acquisto e'
	 * l'istante corrente.
	 */
	@Transactional
	public Biglietto acquista(Spettacolo spettacolo, Settore settore, int quantita, Utente utente) {
		if (quantita < 1) {
			throw new DisponibilitaInsufficienteException();
		}
		int disponibili = this.postiDisponibili(spettacolo, settore);
		if (quantita > disponibili) {
			throw new DisponibilitaInsufficienteException();
		}

		Biglietto biglietto = new Biglietto();
		biglietto.setQuantita(quantita);
		biglietto.setDataAcquisto(LocalDateTime.now());
		biglietto.setUtente(utente);
		biglietto.setSpettacolo(spettacolo);
		biglietto.setSettore(settore);

		return this.bigliettoRepository.save(biglietto);
	}

	// I biglietti di un utente, dal piu' recente.
	@Transactional(readOnly = true)
	public List<Biglietto> findByUtente(Utente utente) {
		return this.bigliettoRepository.findByUtenteOrderByDataAcquistoDesc(utente);
	}
}
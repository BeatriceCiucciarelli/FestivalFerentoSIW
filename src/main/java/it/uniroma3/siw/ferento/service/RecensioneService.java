package it.uniroma3.siw.ferento.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.ferento.model.Recensione;
import it.uniroma3.siw.ferento.model.Spettacolo;
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

	// Voto medio di uno spettacolo. La query puo' restituire null (nessuna
	// recensione): in quel caso restituiamo 0.0.
	@Transactional(readOnly = true)
	public double votoMedio(Spettacolo spettacolo) {
		Double media = this.recensioneRepository.votoMedio(spettacolo);
		return media != null ? media : 0.0;
	}
}
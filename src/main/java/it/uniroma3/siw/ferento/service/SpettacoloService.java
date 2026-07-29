package it.uniroma3.siw.ferento.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.ferento.exception.SpettacoloNonTrovatoException;
import it.uniroma3.siw.ferento.model.Spettacolo;
import it.uniroma3.siw.ferento.repository.SpettacoloRepository;

@Service
public class SpettacoloService {

	private final SpettacoloRepository spettacoloRepository;

	public SpettacoloService(SpettacoloRepository spettacoloRepository) {
		this.spettacoloRepository = spettacoloRepository;
	}

	/*
	 * Restituisce tutti gli spettacoli, ordinati per data crescente.
	 * @Transactional(readOnly = true): operazione di sola lettura.
	 * Sort.by(...) ordina gia' a livello di query SQL (ORDER BY data_ora).
	 */
	@Transactional(readOnly = true)
	public List<Spettacolo> findAll() {
		return this.spettacoloRepository.findAll(Sort.by(Sort.Direction.ASC, "dataOra"));
	}

	/*
	 * Restituisce un singolo spettacolo dato l'id.
	 *
	 * findById restituisce un Optional (lo spettacolo potrebbe non esistere);
	 * orElseThrow lancia la nostra eccezione se l'Optional e' vuoto, cosi'
	 * il chiamante riceve uno spettacolo garantito oppure un 404.
	 */
	@Transactional(readOnly = true)
	public Spettacolo findById(Long id) {
		return this.spettacoloRepository.findById(id)
			.orElseThrow(() -> new SpettacoloNonTrovatoException());
	}
}
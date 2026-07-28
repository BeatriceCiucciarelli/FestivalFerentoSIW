package it.uniroma3.siw.ferento.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	 *
	 * @Transactional(readOnly = true): operazione di sola lettura. Segnala
	 * a Hibernate che non ci saranno modifiche da tracciare, un'ottimizzazione
	 * adatta alle query di consultazione.
	 *
	 * Sort.by(...) chiede al repository di ordinare gia' a livello di query
	 * SQL (ORDER BY data_ora), invece di ordinare in memoria dopo.
	 */
	@Transactional(readOnly = true)
	public List<Spettacolo> findAll() {
		return this.spettacoloRepository.findAll(Sort.by(Sort.Direction.ASC, "dataOra"));
	}
}
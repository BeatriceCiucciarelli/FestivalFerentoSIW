package it.uniroma3.siw.ferento.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.ferento.model.Settore;
import it.uniroma3.siw.ferento.repository.SettoreRepository;

@Service
public class SettoreService {

	private final SettoreRepository settoreRepository;

	public SettoreService(SettoreRepository settoreRepository) {
		this.settoreRepository = settoreRepository;
	}

	/*
	 * Tutti i settori, ordinati per prezzo crescente.
	 * I settori sono condivisi da tutti gli spettacoli (location fissa),
	 * quindi questa lista vale per qualunque spettacolo.
	 */
	@Transactional(readOnly = true)
	public List<Settore> findAll() {
		return this.settoreRepository.findAll(Sort.by(Sort.Direction.ASC, "prezzo"));
	}
}
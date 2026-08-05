package it.uniroma3.siw.ferento.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.ferento.exception.ArtistaNonTrovatoException;
import it.uniroma3.siw.ferento.model.Artista;
import it.uniroma3.siw.ferento.repository.ArtistaRepository;

@Service
public class ArtistaService {

	private final ArtistaRepository artistaRepository;

	public ArtistaService(ArtistaRepository artistaRepository) {
		this.artistaRepository = artistaRepository;
	}

	// Tutti gli artisti, in ordine alfabetico.
	@Transactional(readOnly = true)
	public List<Artista> findAll() {
		return this.artistaRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
	}

	// Un singolo artista (404 se non esiste).
	@Transactional(readOnly = true)
	public Artista findById(Long id) {
		return this.artistaRepository.findById(id)
			.orElseThrow(() -> new ArtistaNonTrovatoException());
	}
}
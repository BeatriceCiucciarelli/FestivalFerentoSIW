package it.uniroma3.siw.ferento.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.ferento.exception.SpettacoloNonTrovatoException;
import it.uniroma3.siw.ferento.model.Artista;
import it.uniroma3.siw.ferento.model.Spettacolo;
import it.uniroma3.siw.ferento.repository.SpettacoloRepository;

@Service
public class SpettacoloService {

	private final SpettacoloRepository spettacoloRepository;

	public SpettacoloService(SpettacoloRepository spettacoloRepository) {
		this.spettacoloRepository = spettacoloRepository;
	}

	// Tutti gli spettacoli, ordinati per data crescente.
	@Transactional(readOnly = true)
	public List<Spettacolo> findAll() {
		return this.spettacoloRepository.findAll(Sort.by(Sort.Direction.ASC, "dataOra"));
	}

	// Un singolo spettacolo (404 se non esiste).
	@Transactional(readOnly = true)
	public Spettacolo findById(Long id) {
		return this.spettacoloRepository.findById(id)
			.orElseThrow(() -> new SpettacoloNonTrovatoException());
	}

	// Crea un nuovo spettacolo, associandolo all'artista scelto.
	@Transactional
	public Spettacolo salva(Spettacolo spettacolo, Artista artista) {
		spettacolo.setArtista(artista);
		return this.spettacoloRepository.save(spettacolo);
	}

	/*
	 * Aggiorna uno spettacolo esistente: carica quello sul database, vi copia
	 * i campi dal form (incluso il nuovo artista) e salva.
	 */
	@Transactional
	public Spettacolo aggiorna(Long id, Spettacolo dati, Artista artista) {
		Spettacolo spettacolo = this.findById(id);
		spettacolo.setTitolo(dati.getTitolo());
		spettacolo.setDescrizione(dati.getDescrizione());
		spettacolo.setDataOra(dati.getDataOra());
		spettacolo.setGenere(dati.getGenere());
		spettacolo.setImmagine(dati.getImmagine());
		spettacolo.setArtista(artista);
		return this.spettacoloRepository.save(spettacolo);
	}

	/*
	 * Elimina uno spettacolo. Grazie a cascade = ALL + orphanRemoval sui
	 * lati biglietti e recensioni, Hibernate cancella automaticamente anche
	 * i biglietti e le recensioni collegati, evitando violazioni di FK.
	 */
	@Transactional
	public void elimina(Long id) {
		Spettacolo spettacolo = this.findById(id);
		this.spettacoloRepository.delete(spettacolo);
	}
}
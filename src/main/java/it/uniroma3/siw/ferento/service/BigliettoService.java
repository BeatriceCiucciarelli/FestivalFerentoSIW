package it.uniroma3.siw.ferento.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.ferento.dto.DisponibilitaSettore;
import it.uniroma3.siw.ferento.model.Settore;
import it.uniroma3.siw.ferento.model.Spettacolo;
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

	// Posti ancora disponibili = capienza del settore - posti venduti.
	@Transactional(readOnly = true)
	public int postiDisponibili(Spettacolo spettacolo, Settore settore) {
		return settore.getCapienza() - this.postiVenduti(spettacolo, settore);
	}

	/*
	 * Per uno spettacolo, costruisce la lista "settore + posti disponibili"
	 * per tutti i settori dati. Restituisce DTO, non entita': sono dati
	 * pronti per la vista (o per un endpoint REST).
	 */
	@Transactional(readOnly = true)
	public List<DisponibilitaSettore> disponibilitaPerSpettacolo(Spettacolo spettacolo, List<Settore> settori) {
		List<DisponibilitaSettore> risultato = new ArrayList<>();
		for (Settore settore : settori) {
			int disponibili = this.postiDisponibili(spettacolo, settore);
			risultato.add(new DisponibilitaSettore(settore, disponibili));
		}
		return risultato;
	}
}
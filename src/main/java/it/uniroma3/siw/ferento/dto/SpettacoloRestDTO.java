package it.uniroma3.siw.ferento.dto;

import it.uniroma3.siw.ferento.model.Spettacolo;

/*
 * DTO usato per esporre uno spettacolo tramite l'API REST (JSON).
 *
 * Non serializziamo l'entita' Spettacolo direttamente: contiene relazioni
 * (artista, recensioni, biglietti) che porterebbero Jackson in cicli di
 * serializzazione o a toccare collezioni caricate in modo lazy. Qui
 * esponiamo solo i campi "piatti" che servono al catalogo React.
 *
 * Questo DTO corrisponde all'interfaccia TypeScript che scriveremo lato
 * client (il "contratto" dell'API).
 */
public class SpettacoloRestDTO {

	private Long id;
	private String titolo;
	private String descrizione;
	private String genere;
	private String dataOra;      // in formato ISO, es. "2026-07-12T21:00"
	private String artistaNome;
	private String immagine;

	// Costruttore che ricava i campi da un'entita' Spettacolo.
	public SpettacoloRestDTO(Spettacolo s) {
		this.id = s.getId();
		this.titolo = s.getTitolo();
		this.descrizione = s.getDescrizione();
		this.genere = s.getGenere().name();
		this.dataOra = s.getDataOra().toString();
		this.artistaNome = s.getArtista().getNome();
		this.immagine = s.getImmagine();
	}

	public Long getId() {
		return id;
	}

	public String getTitolo() {
		return titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getGenere() {
		return genere;
	}

	public String getDataOra() {
		return dataOra;
	}

	public String getArtistaNome() {
		return artistaNome;
	}

	public String getImmagine() {
		return immagine;
	}
}
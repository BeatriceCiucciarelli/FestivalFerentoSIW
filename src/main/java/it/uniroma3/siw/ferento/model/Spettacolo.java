package it.uniroma3.siw.ferento.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Spettacolo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 255)
	@Column(nullable = false)
	private String titolo;

	@Size(max = 2000)
	@Column(length = 2000)
	private String descrizione;

	// Data E ora della rappresentazione: LocalDateTime tiene entrambe.
	// Non mettiamo @Future: vogliamo poter avere in catalogo sia
	// spettacoli futuri (acquistabili) sia passati (recensibili).
	@NotNull
	@Column(nullable = false)
	private LocalDateTime dataOra;

	// Genere come enum: l'insieme dei valori e' chiuso e noto in anticipo.
	// @Enumerated(EnumType.STRING) salva sul database la stringa
	// ("TEATRO", "DANZA"...) invece dell'indice numerico: piu' leggibile
	// nel DB e, soprattutto, robusto se un giorno riordini le costanti.
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Genere genere;

	public enum Genere {
		TEATRO,
		DANZA,
		MUSICA,
		OPERA
	}

	// Nome del file immagine (o URL). Facoltativo: uno spettacolo puo'
	// esistere anche senza locandina. La gestione dell'upload vero e
	// proprio e' un bonus, per ora e' solo una stringa.
	@Size(max = 255)
	private String immagine;

	// Lato "molti" e PROPRIETARIO della relazione con Artista.
	// @JoinColumn crea nella tabella spettacolo la colonna artista_id,
	// che e' la chiave esterna verso la tabella artista.
	// nullable = false: ogni spettacolo deve avere un artista.
	@ManyToOne
	@JoinColumn(name = "artista_id", nullable = false)
	private Artista artista;

	public Spettacolo() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDateTime getDataOra() {
		return dataOra;
	}

	public void setDataOra(LocalDateTime dataOra) {
		this.dataOra = dataOra;
	}

	public Genere getGenere() {
		return genere;
	}

	public void setGenere(Genere genere) {
		this.genere = genere;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Spettacolo other = (Spettacolo) obj;
		return Objects.equals(id, other.id);
	}
}
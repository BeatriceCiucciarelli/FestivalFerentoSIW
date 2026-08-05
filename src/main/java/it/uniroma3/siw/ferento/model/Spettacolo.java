package it.uniroma3.siw.ferento.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	// Data E ora della rappresentazione.
	// @DateTimeFormat fissa il formato usato nei form (input datetime-local),
	// sia per leggere il valore inviato sia per riempire il campo in modifica.
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(nullable = false)
	private LocalDateTime dataOra;

	// Genere come enum, salvato come stringa leggibile sul DB.
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

	// Nome del file immagine (o URL). Facoltativo.
	@Size(max = 255)
	private String immagine;

	// Lato "molti" e proprietario della relazione con Artista.
	@ManyToOne
	@JoinColumn(name = "artista_id", nullable = false)
	private Artista artista;

	// --- Lati inversi ---

	// Biglietti venduti: cascade ALL + orphanRemoval => eliminando lo
	// spettacolo si eliminano anche i biglietti collegati.
	@OneToMany(mappedBy = "spettacolo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Biglietto> biglietti = new ArrayList<>();

	// Recensioni: stessa cascata.
	@OneToMany(mappedBy = "spettacolo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Recensione> recensioni = new ArrayList<>();

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

	public List<Biglietto> getBiglietti() {
		return biglietti;
	}

	public void setBiglietti(List<Biglietto> biglietti) {
		this.biglietti = biglietti;
	}

	public List<Recensione> getRecensioni() {
		return recensioni;
	}

	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
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
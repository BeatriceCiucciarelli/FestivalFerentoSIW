package it.uniroma3.siw.ferento.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Artista {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 255)
	@Column(nullable = false)
	private String nome;

	// Descrizione facoltativa (biografia, note): niente @NotBlank,
	// niente nullable=false. Colonna piu' lunga per un testo esteso.
	@Size(max = 2000)
	@Column(length = 2000)
	private String descrizione;

	// Lato "uno" della relazione uno-a-molti con Spettacolo.
	// mappedBy = "artista" indica che la relazione e' gia' gestita dal
	// campo "artista" nella classe Spettacolo (il lato proprietario, che
	// possiede la colonna della chiave esterna). Qui NON creiamo quindi
	// nessuna colonna aggiuntiva: questo e' solo il lato "inverso", comodo
	// per navigare da un artista ai suoi spettacoli.
	//
	// Scelta di design: nessun cascade e nessun orphanRemoval. Uno
	// spettacolo e' un'entita' di primo piano (avra' biglietti e
	// recensioni collegati), quindi cancellare un artista non deve
	// eliminare a catena i suoi spettacoli.
	@OneToMany(mappedBy = "artista")
	private List<Spettacolo> spettacoli = new ArrayList<>();

	public Artista() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Spettacolo> getSpettacoli() {
		return spettacoli;
	}

	public void setSpettacoli(List<Spettacolo> spettacoli) {
		this.spettacoli = spettacoli;
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
		Artista other = (Artista) obj;
		return Objects.equals(id, other.id);
	}
}
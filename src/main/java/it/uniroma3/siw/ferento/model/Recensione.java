package it.uniroma3.siw.ferento.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Recensione {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 1000)
	@Column(nullable = false, length = 1000)
	private String testo;

	// Voto in stelle da 1 a 5, garantito dai vincoli @Min/@Max.
	@NotNull
	@Min(value = 1)
	@Max(value = 5)
	@Column(nullable = false)
	private Integer voto;

	// --- Le due relazioni ---
	// Autore della recensione e spettacolo recensito: due chiavi esterne
	// nella tabella recensione, entrambe obbligatorie.

	@ManyToOne
	@JoinColumn(name = "utente_id", nullable = false)
	private Utente utente;

	@ManyToOne
	@JoinColumn(name = "spettacolo_id", nullable = false)
	private Spettacolo spettacolo;

	public Recensione() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Integer getVoto() {
		return voto;
	}

	public void setVoto(Integer voto) {
		this.voto = voto;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Spettacolo getSpettacolo() {
		return spettacolo;
	}

	public void setSpettacolo(Spettacolo spettacolo) {
		this.spettacolo = spettacolo;
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
		Recensione other = (Recensione) obj;
		return Objects.equals(id, other.id);
	}
}
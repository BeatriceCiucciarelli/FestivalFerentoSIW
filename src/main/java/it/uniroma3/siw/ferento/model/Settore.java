package it.uniroma3.siw.ferento.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class Settore {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Nome del settore, es. "Gradinata A", "Platea".
	@NotBlank
	@Size(max = 255)
	@Column(nullable = false)
	private String nome;

	// Prezzo del biglietto per questo settore.
	// BigDecimal (non Double) perche' per il denaro servono decimali
	// esatti: i tipi in virgola mobile introducono errori di
	// arrotondamento. precision=6, scale=2 => fino a 9999.99.
	@NotNull
	@Positive
	@Column(nullable = false, precision = 6, scale = 2)
	private BigDecimal prezzo;

	// Numero massimo di posti disponibili nel settore.
	// Essendo la location fissa, questo valore vale per ogni spettacolo.
	@NotNull
	@Min(value = 1)
	@Column(nullable = false)
	private Integer capienza;

	// --- Lato inverso (solo navigazione, nessuna cascata) ---
	// Biglietti venduti per questo settore (attraverso tutti gli
	// spettacoli). Nessun cascade: i settori sono condivisi e permanenti,
	// non si eliminano, e non devono trascinare via biglietti.
	@OneToMany(mappedBy = "settore")
	private List<Biglietto> biglietti = new ArrayList<>();

	public Settore() {
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

	public BigDecimal getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}

	public Integer getCapienza() {
		return capienza;
	}

	public void setCapienza(Integer capienza) {
		this.capienza = capienza;
	}

	public List<Biglietto> getBiglietti() {
		return biglietti;
	}

	public void setBiglietti(List<Biglietto> biglietti) {
		this.biglietti = biglietti;
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
		Settore other = (Settore) obj;
		return Objects.equals(id, other.id);
	}
}
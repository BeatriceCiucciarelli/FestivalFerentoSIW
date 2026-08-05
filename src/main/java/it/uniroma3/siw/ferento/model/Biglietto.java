package it.uniroma3.siw.ferento.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Biglietto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Un Biglietto rappresenta UN ACQUISTO: quantita indica quanti posti
	// sono stati comprati in una sola operazione.
	@NotNull
	@Min(value = 1)
	@Column(nullable = false)
	private Integer quantita;

	// Istante dell'acquisto, valorizzato dal service al momento della vendita.
	@NotNull
	@Column(nullable = false)
	private LocalDateTime dataAcquisto;

	// --- Le tre relazioni che fanno di Biglietto un'entita' "ponte" ---

	@ManyToOne
	@JoinColumn(name = "utente_id", nullable = false)
	private Utente utente;

	@ManyToOne
	@JoinColumn(name = "spettacolo_id", nullable = false)
	private Spettacolo spettacolo;

	@ManyToOne
	@JoinColumn(name = "settore_id", nullable = false)
	private Settore settore;

	public Biglietto() {
	}

	/*
	 * Prezzo totale dell'acquisto = prezzo del settore x quantita'.
	 * @Transient: e' un valore CALCOLATO, non una colonna del database;
	 * viene ricalcolato ogni volta che serve, non memorizzato.
	 */
	@Transient
	public BigDecimal getPrezzoTotale() {
		if (this.settore == null || this.quantita == null) {
			return BigDecimal.ZERO;
		}
		return this.settore.getPrezzo().multiply(BigDecimal.valueOf(this.quantita));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantita() {
		return quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}

	public LocalDateTime getDataAcquisto() {
		return dataAcquisto;
	}

	public void setDataAcquisto(LocalDateTime dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
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

	public Settore getSettore() {
		return settore;
	}

	public void setSettore(Settore settore) {
		this.settore = settore;
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
		Biglietto other = (Biglietto) obj;
		return Objects.equals(id, other.id);
	}
}
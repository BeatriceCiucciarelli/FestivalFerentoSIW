package it.uniroma3.siw.ferento.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Biglietto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Un Biglietto rappresenta UN ACQUISTO: quantita indica quanti posti
	// sono stati comprati in una sola operazione. La disponibilita' di un
	// settore si calcolera' quindi come capienza - SOMMA(quantita).
	@NotNull
	@Min(value = 1)
	@Column(nullable = false)
	private Integer quantita;

	// Istante dell'acquisto. Non e' inserito dall'utente: lo valorizza il
	// service al momento della creazione, con la data/ora corrente.
	@NotNull
	@Column(nullable = false)
	private LocalDateTime dataAcquisto;

	// --- Le tre relazioni che fanno di Biglietto un'entita' "ponte" ---
	// Ciascuna @ManyToOne genera una chiave esterna nella tabella biglietto.
	// Tutte nullable = false: un biglietto senza utente, spettacolo o
	// settore non avrebbe senso.

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
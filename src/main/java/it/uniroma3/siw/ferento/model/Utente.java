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
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// unique = true: non possono esistere due utenti con lo stesso
	// username. Hibernate genera un vincolo di unicita' sulla colonna.
	@NotBlank
	@Size(min = 3, max = 50)
	@Column(nullable = false, unique = true)
	private String username;

	// Il vincolo @Size(min = 6) vale sulla password IN CHIARO, controllata
	// dal @Valid nel controller di registrazione. Il service poi la cifra
	// con BCrypt (circa 60 caratteri), che sta comodamente nel varchar(255)
	// di default della colonna.
	@NotBlank
	@Size(min = 6)
	@Column(nullable = false)
	private String password;

	// Ruolo dell'utente: "USER" oppure "ADMIN".
	// Il SecurityConfig leggera' questo valore e lo usera' direttamente
	// come authority (tramite la query del JdbcUserDetailsManager).
	@Column(nullable = false)
	private String ruolo;

	// --- Lati inversi (solo navigazione, nessuna cascata) ---
	// Servono per i casi d'uso "i miei biglietti" e "le mie recensioni".
	// Nessun cascade: eliminare un utente non e' un caso d'uso previsto e
	// non deve trascinare via biglietti o recensioni.

	@OneToMany(mappedBy = "utente")
	private List<Biglietto> biglietti = new ArrayList<>();

	@OneToMany(mappedBy = "utente")
	private List<Recensione> recensioni = new ArrayList<>();

	public Utente() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
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
		Utente other = (Utente) obj;
		return Objects.equals(id, other.id);
	}
}
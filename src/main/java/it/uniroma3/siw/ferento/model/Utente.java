package it.uniroma3.siw.ferento.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
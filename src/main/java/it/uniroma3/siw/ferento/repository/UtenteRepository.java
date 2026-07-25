package it.uniroma3.siw.ferento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.ferento.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

	// Metodo "derivato dal nome": Spring Data costruisce da solo la query
	// leggendo "findByUsername" come "trova l'Utente con quell'username".
	// Optional segnala esplicitamente che il risultato potrebbe non
	// esistere (username non registrato), evitando il ritorno di null.
	Optional<Utente> findByUsername(String username);
}
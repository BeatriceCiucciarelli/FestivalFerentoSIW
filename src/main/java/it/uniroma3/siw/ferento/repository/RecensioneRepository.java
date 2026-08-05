package it.uniroma3.siw.ferento.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.ferento.model.Recensione;
import it.uniroma3.siw.ferento.model.Spettacolo;
import it.uniroma3.siw.ferento.model.Utente;

public interface RecensioneRepository extends JpaRepository<Recensione, Long> {

	// Le recensioni di uno spettacolo, dalla piu' recente (id decrescente).
	List<Recensione> findBySpettacoloOrderByIdDesc(Spettacolo spettacolo);

	// Media dei voti di uno spettacolo (JPQL, funzione di aggregazione avg).
	// Restituisce null se lo spettacolo non ha ancora recensioni.
	@Query("select avg(r.voto) from Recensione r where r.spettacolo = :spettacolo")
	Double votoMedio(@Param("spettacolo") Spettacolo spettacolo);

	// La (eventuale) recensione di un dato utente per un dato spettacolo.
	// Serve sia per l'interfaccia (mostrare "scrivi" o "hai gia' recensito")
	// sia per impedire una seconda recensione dello stesso utente.
	Optional<Recensione> findByUtenteAndSpettacolo(Utente utente, Spettacolo spettacolo);
}
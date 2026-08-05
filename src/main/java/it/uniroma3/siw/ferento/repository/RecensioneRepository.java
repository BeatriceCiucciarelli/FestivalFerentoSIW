package it.uniroma3.siw.ferento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.ferento.model.Recensione;
import it.uniroma3.siw.ferento.model.Spettacolo;

public interface RecensioneRepository extends JpaRepository<Recensione, Long> {

	// Query DERIVATA dal nome: le recensioni di uno spettacolo, ordinate
	// dalla piu' recente (id decrescente).
	List<Recensione> findBySpettacoloOrderByIdDesc(Spettacolo spettacolo);

	// Query JPQL scritta a mano: media dei voti di uno spettacolo.
	// JPQL lavora sugli OGGETTI (Recensione r, r.voto, r.spettacolo), non
	// sulle tabelle. avg(...) e' una funzione di aggregazione.
	// Restituisce Double (null se lo spettacolo non ha ancora recensioni).
	@Query("select avg(r.voto) from Recensione r where r.spettacolo = :spettacolo")
	Double votoMedio(@Param("spettacolo") Spettacolo spettacolo);
}
package it.uniroma3.siw.ferento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.ferento.model.Biglietto;
import it.uniroma3.siw.ferento.model.Settore;
import it.uniroma3.siw.ferento.model.Spettacolo;
import it.uniroma3.siw.ferento.model.Utente;

public interface BigliettoRepository extends JpaRepository<Biglietto, Long> {

	// Somma delle quantita' vendute per uno spettacolo in un settore.
	// coalesce(sum(...), 0) restituisce 0 quando non c'e' ancora nulla.
	@Query("select coalesce(sum(b.quantita), 0) from Biglietto b "
		+ "where b.spettacolo = :spettacolo and b.settore = :settore")
	Long postiVenduti(@Param("spettacolo") Spettacolo spettacolo, @Param("settore") Settore settore);

	// I biglietti di un utente, dal piu' recente (per "i miei biglietti").
	List<Biglietto> findByUtenteOrderByDataAcquistoDesc(Utente utente);
}
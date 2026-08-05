package it.uniroma3.siw.ferento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.ferento.model.Biglietto;
import it.uniroma3.siw.ferento.model.Settore;
import it.uniroma3.siw.ferento.model.Spettacolo;

public interface BigliettoRepository extends JpaRepository<Biglietto, Long> {

	/*
	 * Somma delle quantita' vendute per un dato spettacolo in un dato settore.
	 * coalesce(sum(...), 0): se non e' stato venduto nulla, sum vale null;
	 * coalesce lo trasforma in 0, cosi' il calcolo della disponibilita' a
	 * valle non deve gestire il caso null.
	 *
	 * Questa e' la query resa possibile dalla scelta di modellare il
	 * Biglietto come "un acquisto con quantita'".
	 */
	@Query("select coalesce(sum(b.quantita), 0) from Biglietto b "
		+ "where b.spettacolo = :spettacolo and b.settore = :settore")
	Long postiVenduti(@Param("spettacolo") Spettacolo spettacolo, @Param("settore") Settore settore);
}
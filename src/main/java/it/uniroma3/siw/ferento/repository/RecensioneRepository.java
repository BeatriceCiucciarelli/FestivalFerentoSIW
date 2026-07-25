package it.uniroma3.siw.ferento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.ferento.model.Recensione;

// Estendendo JpaRepository otteniamo gia' pronti save, findById,
// findAll, delete, count... senza scrivere alcuna implementazione:
// Spring Data la genera automaticamente a runtime.
public interface RecensioneRepository extends JpaRepository<Recensione, Long> {

}
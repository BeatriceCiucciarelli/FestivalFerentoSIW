package it.uniroma3.siw.ferento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Lanciata quando si richiede uno spettacolo con un id inesistente.
 *
 * @ResponseStatus(HttpStatus.NOT_FOUND): quando questa eccezione risale
 * fino a Spring senza essere catturata, Spring risponde automaticamente
 * con lo stato HTTP 404 (Not Found), senza bisogno di codice aggiuntivo
 * nel controller.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SpettacoloNonTrovatoException extends RuntimeException {

	public SpettacoloNonTrovatoException() {
		super("Spettacolo non trovato");
	}
}
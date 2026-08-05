package it.uniroma3.siw.ferento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecensioneNonTrovataException extends RuntimeException {

	public RecensioneNonTrovataException() {
		super("Recensione non trovata");
	}
}
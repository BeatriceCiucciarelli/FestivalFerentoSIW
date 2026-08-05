package it.uniroma3.siw.ferento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Lanciata quando un utente tenta un'operazione su una risorsa che non gli
 * appartiene (es. modificare la recensione di un altro utente).
 * @ResponseStatus(FORBIDDEN): Spring risponde con lo stato HTTP 403.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessoNegatoException extends RuntimeException {

	public AccessoNegatoException() {
		super("Non hai i permessi per questa operazione");
	}
}
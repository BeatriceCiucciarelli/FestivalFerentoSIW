package it.uniroma3.siw.ferento.exception;

/*
 * Eccezione lanciata dal UtenteService quando si tenta di registrare un
 * username gia' presente nel database. Estende RuntimeException (eccezione
 * "unchecked"): non obbliga il chiamante a un try/catch: la intercetteremo
 * in un punto unico, nel controller di registrazione.
 */
public class UsernameGiaRegistratoException extends RuntimeException {

	public UsernameGiaRegistratoException() {
		super("Username già registrato");
	}
}
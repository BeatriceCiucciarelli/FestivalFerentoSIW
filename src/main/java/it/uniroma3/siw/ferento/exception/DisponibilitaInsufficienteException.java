package it.uniroma3.siw.ferento.exception;

/*
 * Lanciata dal BigliettoService quando si tenta di acquistare piu' posti di
 * quelli disponibili (o una quantita' non valida). Non ha @ResponseStatus:
 * viene gestita nel controller, che riporta l'utente al dettaglio con un
 * messaggio, invece di mostrare una pagina d'errore.
 */
public class DisponibilitaInsufficienteException extends RuntimeException {

	public DisponibilitaInsufficienteException() {
		super("Posti non disponibili nella quantità richiesta");
	}
}
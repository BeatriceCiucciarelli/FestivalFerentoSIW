package it.uniroma3.siw.ferento.dto;

import it.uniroma3.siw.ferento.model.Settore;

/*
 * DTO (Data Transfer Object): un semplice contenitore di dati per la vista.
 * NON e' un'entita' (nessuna annotazione JPA, non finisce sul database):
 * serve solo a portare insieme, verso il template, un settore e il numero
 * di posti ancora disponibili per un certo spettacolo (un valore calcolato,
 * non memorizzato).
 */
public class DisponibilitaSettore {

	private final Settore settore;
	private final int postiDisponibili;

	public DisponibilitaSettore(Settore settore, int postiDisponibili) {
		this.settore = settore;
		this.postiDisponibili = postiDisponibili;
	}

	public Settore getSettore() {
		return settore;
	}

	public int getPostiDisponibili() {
		return postiDisponibili;
	}
}
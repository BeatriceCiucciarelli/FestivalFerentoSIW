// La "forma" di uno spettacolo come arriva dal backend (GET /rest/spettacoli).
// Corrisponde campo per campo a SpettacoloRestDTO lato Java: e' il contratto
// dell'API visto dal client. Il "?" indica un campo che puo' mancare (null).

export interface Spettacolo {
  id: number;
  titolo: string;
  descrizione: string;
  genere: string;
  dataOra: string;      // ISO, es. "2026-07-12T21:00"
  artistaNome: string;
  immagine?: string | null;
}

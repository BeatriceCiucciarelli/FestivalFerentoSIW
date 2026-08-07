import api from "./api";
import type { Spettacolo } from "../types";

// Chiama GET /rest/spettacoli e restituisce la lista tipizzata.
// I componenti useranno questa funzione senza sapere COME i dati vengono
// recuperati: separare le chiamate HTTP dai componenti tiene il codice in ordine.
export async function getSpettacoli(): Promise<Spettacolo[]> {
  const risposta = await api.get<Spettacolo[]>("/rest/spettacoli");
  return risposta.data;
}

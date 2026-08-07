import axios from "axios";

// Istanza axios condivisa da tutti i service.
// baseURL: l'indirizzo del backend Spring. Ogni chiamata (es. "/rest/spettacoli")
// viene aggiunta a questo indirizzo -> http://localhost:8080/rest/spettacoli.
const api = axios.create({
  baseURL: "http://localhost:8080",
});

export default api;

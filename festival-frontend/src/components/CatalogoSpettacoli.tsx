import { useEffect, useMemo, useState } from "react";
import {
  Box,
  Card,
  CardContent,
  Chip,
  CircularProgress,
  Alert,
  Typography,
  TextField,
  Stack,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import type { Spettacolo } from "../types";
import { getSpettacoli } from "../services/spettacoloService";

function formattaData(iso: string): string {
  const d = new Date(iso);
  const data = d.toLocaleDateString("it-IT");
  const ora = d.toLocaleTimeString("it-IT", { hour: "2-digit", minute: "2-digit" });
  return `${data} · ${ora}`;
}

// I criteri di ordinamento disponibili.
type Ordine = "data" | "titolo-az" | "titolo-za";

function CatalogoSpettacoli() {
  // --- Dati dal backend (caricati una volta sola) ---
  const [spettacoli, setSpettacoli] = useState<Spettacolo[]>([]);
  const [caricamento, setCaricamento] = useState<boolean>(true);
  const [errore, setErrore] = useState<string | null>(null);

  // --- Stato dei filtri (vive solo sul client) ---
  const [genereScelto, setGenereScelto] = useState<string | null>(null);
  const [ricerca, setRicerca] = useState<string>("");
  const [ordine, setOrdine] = useState<Ordine>("data");

  useEffect(() => {
    getSpettacoli()
      .then((dati) => setSpettacoli(dati))
      .catch(() => setErrore("Impossibile caricare gli spettacoli. È attivo il backend?"))
      .finally(() => setCaricamento(false));
  }, []);

  // Elenco dei generi presenti nei dati, senza duplicati: da qui nascono i chip.
  // useMemo ricalcola solo se "spettacoli" cambia (ottimizzazione).
  const generi = useMemo(() => {
    return Array.from(new Set(spettacoli.map((s) => s.genere)));
  }, [spettacoli]);

  // La lista finale mostrata: parte da tutti gli spettacoli e applica, nell'ordine,
  // il filtro per genere, la ricerca per titolo e l'ordinamento. E' ricalcolata
  // ogni volta che cambiano i dati o uno dei filtri: e' qui il "tempo reale".
  const spettacoliMostrati = useMemo(() => {
    let risultato = [...spettacoli];

    if (genereScelto) {
      risultato = risultato.filter((s) => s.genere === genereScelto);
    }

    const q = ricerca.trim().toLowerCase();
    if (q) {
      risultato = risultato.filter((s) => s.titolo.toLowerCase().includes(q));
    }

    risultato.sort((a, b) => {
      if (ordine === "titolo-az") return a.titolo.localeCompare(b.titolo);
      if (ordine === "titolo-za") return b.titolo.localeCompare(a.titolo);
      // "data": ordine cronologico crescente
      return a.dataOra.localeCompare(b.dataOra);
    });

    return risultato;
  }, [spettacoli, genereScelto, ricerca, ordine]);

  if (caricamento) {
    return (
      <Box sx={{ display: "flex", justifyContent: "center", py: 6 }}>
        <CircularProgress />
      </Box>
    );
  }

  if (errore) {
    return <Alert severity="error" sx={{ my: 3 }}>{errore}</Alert>;
  }

  return (
    <Box>
      {/* --- Barra dei filtri --- */}
      <Stack spacing={2} sx={{ mb: 3 }}>
        {/* Chip per genere: "Tutti" + un chip per ogni genere.
            Il chip attivo e' colorato; cliccarlo di nuovo torna a "Tutti". */}
        <Stack direction="row" spacing={1} useFlexGap flexWrap="wrap" alignItems="center">
          <Chip
            label="Tutti"
            color={genereScelto === null ? "primary" : "default"}
            onClick={() => setGenereScelto(null)}
          />
          {generi.map((g) => (
            <Chip
              key={g}
              label={g}
              color={genereScelto === g ? "primary" : "default"}
              onClick={() => setGenereScelto(genereScelto === g ? null : g)}
            />
          ))}
        </Stack>

        {/* Ricerca per titolo + ordinamento */}
        <Stack direction={{ xs: "column", sm: "row" }} spacing={2}>
          <TextField
            label="Cerca per titolo"
            variant="outlined"
            size="small"
            value={ricerca}
            onChange={(e) => setRicerca(e.target.value)}
            sx={{ flexGrow: 1 }}
          />
          <FormControl size="small" sx={{ minWidth: 200 }}>
            <InputLabel id="ordine-label">Ordina per</InputLabel>
            <Select
              labelId="ordine-label"
              label="Ordina per"
              value={ordine}
              onChange={(e) => setOrdine(e.target.value as Ordine)}
            >
              <MenuItem value="data">Data</MenuItem>
              <MenuItem value="titolo-az">Titolo A→Z</MenuItem>
              <MenuItem value="titolo-za">Titolo Z→A</MenuItem>
            </Select>
          </FormControl>
        </Stack>

        <Typography variant="body2" color="text.secondary">
          {spettacoliMostrati.length} spettacoli
        </Typography>
      </Stack>

      {/* --- Griglia --- */}
      {spettacoliMostrati.length === 0 ? (
        <Typography color="text.secondary">Nessuno spettacolo corrisponde ai filtri.</Typography>
      ) : (
        <Box
          sx={{
            display: "grid",
            gridTemplateColumns: "repeat(auto-fill, minmax(260px, 1fr))",
            gap: 3,
          }}
        >
          {spettacoliMostrati.map((s) => (
            <Card key={s.id} sx={{ display: "flex", flexDirection: "column" }}>
              <Box
                sx={{
                  height: 140,
                  bgcolor: "primary.main",
                  color: "primary.contrastText",
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                  fontFamily: "'Cinzel', serif",
                  px: 2,
                  textAlign: "center",
                }}
              >
                {s.titolo}
              </Box>
              <CardContent>
                <Chip label={s.genere} size="small" color="secondary" sx={{ mb: 1 }} />
                <Typography variant="h6">{s.titolo}</Typography>
                <Typography variant="body2" color="text.secondary">
                  {formattaData(s.dataOra)}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {s.artistaNome}
                </Typography>
              </CardContent>
            </Card>
          ))}
        </Box>
      )}
    </Box>
  );
}

export default CatalogoSpettacoli;

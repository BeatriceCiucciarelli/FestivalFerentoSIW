import { createTheme } from "@mui/material/styles";

// Tema Material UI personalizzato con l'identita' del Festival di Ferento.
// createTheme e' lo strumento con cui MUI applica una palette e una
// tipografia coerenti a TUTTI i suoi componenti (Button, Chip, Select...),
// cosi' escono col nostro stile invece che con i colori di default.
const theme = createTheme({
  palette: {
    mode: "light",
    primary: {
      main: "#8b3a2f",      // terracotta / rosso pompeiano
      dark: "#63271f",
      contrastText: "#faf6f0",
    },
    secondary: {
      main: "#c9a227",      // oro / ocra
      dark: "#9a7a15",
    },
    background: {
      default: "#faf6f0",   // avorio caldo
      paper: "#ffffff",
    },
    text: {
      primary: "#3a3230",   // bruno scuro
    },
  },
  typography: {
    fontFamily: "'Inter', system-ui, sans-serif",
    // I titoli (h1..h6) usano Cinzel, come nel resto del sito.
    h1: { fontFamily: "'Cinzel', serif" },
    h2: { fontFamily: "'Cinzel', serif" },
    h3: { fontFamily: "'Cinzel', serif" },
    h4: { fontFamily: "'Cinzel', serif" },
    h5: { fontFamily: "'Cinzel', serif" },
    h6: { fontFamily: "'Cinzel', serif" },
  },
  shape: {
    borderRadius: 10,
  },
});

export default theme;

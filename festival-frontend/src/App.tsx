import "@fontsource/cinzel/500.css";
import "@fontsource/cinzel/600.css";
import "@fontsource/inter/400.css";
import "@fontsource/inter/500.css";

import { ThemeProvider, CssBaseline, Container, Typography } from "@mui/material";
import theme from "./theme";
import CatalogoSpettacoli from "./components/CatalogoSpettacoli";

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Container sx={{ py: 5 }}>
        <Typography variant="h3" gutterBottom>
          Festival di Ferento
        </Typography>
        <Typography variant="body1" color="text.secondary" sx={{ mb: 4 }}>
          Catalogo spettacoli
        </Typography>

        <CatalogoSpettacoli />
      </Container>
    </ThemeProvider>
  );
}

export default App;

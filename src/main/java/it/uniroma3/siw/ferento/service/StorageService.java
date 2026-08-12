package it.uniroma3.siw.ferento.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/*
 * Servizio che salva su disco le immagini caricate (le locandine) e
 * restituisce il nome del file salvato, da memorizzare nel campo
 * "immagine" dello Spettacolo.
 */
@Service
public class StorageService {

	// Estensioni immagine accettate.
	private static final Set<String> ESTENSIONI_AMMESSE = Set.of("jpg", "jpeg", "png", "gif", "webp", "svg");

	// Cartella di destinazione (da application.properties: app.upload.dir).
	private final Path cartella;

	public StorageService(@Value("${app.upload.dir}") String dir) {
		this.cartella = Paths.get(dir).toAbsolutePath().normalize();
	}

	/*
	 * Salva il file e restituisce il nome generato.
	 *
	 * Genera un nome UNIVOCO con UUID, mantenendo solo l'estensione: cosi'
	 * due upload non si sovrascrivono e non ci si fida del nome originale
	 * (che potrebbe essere malevolo). Accetta solo estensioni immagine.
	 */
	public String salva(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("Nessun file caricato");
		}

		String originale = file.getOriginalFilename();
		String estensione = "";
		if (originale != null && originale.contains(".")) {
			estensione = originale.substring(originale.lastIndexOf('.') + 1).toLowerCase();
		}
		if (!ESTENSIONI_AMMESSE.contains(estensione)) {
			throw new IllegalArgumentException("Formato immagine non ammesso");
		}

		String nomeFile = UUID.randomUUID().toString() + "." + estensione;
		try {
			Files.createDirectories(this.cartella);
			Files.copy(file.getInputStream(), this.cartella.resolve(nomeFile),
				StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("Errore nel salvataggio dell'immagine", e);
		}
		return nomeFile;
	}
}
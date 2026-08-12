package it.uniroma3.siw.ferento.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * Fa in modo che l'URL /images/** serva le immagini da DUE posizioni:
 *  1. classpath:/static/images/  -> le locandine "di sistema" (le 8 SVG);
 *  2. la cartella esterna degli upload -> le immagini caricate dall'admin.
 *
 * Cosi' il campo "immagine" resta sempre un semplice nome file: che il file
 * sia una locandina di sistema o una caricata, l'URL /images/<nome> funziona
 * in entrambi i casi, e template e React non cambiano.
 */
@Configuration
public class StorageConfig implements WebMvcConfigurer {

	private final String uploadDir;

	public StorageConfig(@Value("${app.upload.dir}") String uploadDir) {
		this.uploadDir = uploadDir;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path cartella = Paths.get(this.uploadDir).toAbsolutePath().normalize();
		// toUri() produce un percorso "file:/..." che Spring serve dal disco.
		String posizioneUpload = cartella.toUri().toString();

		registry.addResourceHandler("/images/**")
			.addResourceLocations("classpath:/static/images/", posizioneUpload);
	}
}
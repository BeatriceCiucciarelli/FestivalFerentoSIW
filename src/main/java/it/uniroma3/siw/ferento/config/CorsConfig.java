package it.uniroma3.siw.ferento.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * Configurazione CORS (Cross-Origin Resource Sharing).
 *
 * Il browser, per sicurezza (Same-Origin Policy), blocca le richieste tra
 * origini diverse. L'app React gira su http://localhost:5173, il backend su
 * http://localhost:8080: origini diverse. Qui dichiariamo che il backend
 * accetta le chiamate agli endpoint /rest/** provenienti da quell'origine.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/rest/**")
			.allowedOrigins("http://localhost:5173")
			.allowedMethods("GET", "POST", "PUT", "DELETE");
	}
}
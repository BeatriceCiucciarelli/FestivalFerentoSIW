package it.uniroma3.siw.ferento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * Configurazione della sicurezza.
 *
 * NOTA: per ora questa classe definisce SOLO il bean PasswordEncoder, che
 * serve gia' al UtenteService per cifrare le password in registrazione.
 * Le regole di autorizzazione (quali pagine sono pubbliche, il form di
 * login, il logout) e l'aggancio alla tabella "utente" tramite
 * JdbcUserDetailsManager verranno aggiunti a QUESTA stessa classe nel
 * passo dedicato alla sicurezza.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/*
	 * Un "bean" e' un oggetto gestito da Spring: creato una volta sola e
	 * reso disponibile a chiunque lo richieda (qui, il UtenteService).
	 *
	 * BCryptPasswordEncoder applica un hash a senso unico con salt casuale
	 * incorporato: dalla stringa salvata non si puo' risalire alla password
	 * originale. Lo stesso encoder verra' usato in fase di login per
	 * verificare la password inserita.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
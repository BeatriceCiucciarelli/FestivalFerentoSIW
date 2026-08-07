package it.uniroma3.siw.ferento.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// Il DataSource (la connessione al database configurata in
	// application.properties) e' creato automaticamente da Spring Boot.
	// Lo riceviamo dal costruttore per passarlo al JdbcUserDetailsManager.
	private final DataSource dataSource;

	public SecurityConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/*
	 * Encoder BCrypt: hash a senso unico con salt casuale. Usato sia in
	 * registrazione (UtenteService) sia, automaticamente, al login per
	 * confrontare la password inserita con quella salvata.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * AUTENTICAZIONE.
	 * JdbcUserDetailsManager legge gli utenti direttamente dal database
	 * tramite due query SQL sulla nostra tabella "utente".
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

		// Query delle credenziali. Spring pretende una colonna "enabled"
		// (utente attivo/disattivo) che la nostra tabella non ha: la
		// simuliamo con "true as enabled", cioe' ogni utente e' sempre attivo.
		manager.setUsersByUsernameQuery(
			"select username, password, true as enabled from utente where username = ?"
		);

		// Query dei ruoli. Il valore della colonna "ruolo" ("USER"/"ADMIN")
		// viene usato direttamente come authority.
		manager.setAuthoritiesByUsernameQuery(
			"select username, ruolo as authority from utente where username = ?"
		);

		return manager;
	}

	/*
	 * AUTORIZZAZIONE.
	 * La catena di filtri che intercetta ogni richiesta HTTP. Le regole
	 * dentro authorizeHttpRequests sono valutate DALL'ALTO VERSO IL BASSO:
	 * vince la prima che corrisponde al percorso della richiesta.
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// Abilita il supporto CORS (usa le regole di CorsConfig).
			.cors(Customizer.withDefaults())
			.authorizeHttpRequests(auth -> auth
				// --- Pagine pubbliche (sola consultazione, in GET) ---
				.requestMatchers(HttpMethod.GET,
					"/", "/login", "/register",
					"/spettacoli", "/spettacoli/**",
					"/artisti", "/artisti/**",
					"/rest/**",
					"/css/**", "/js/**", "/images/**"
				).permitAll()
				// La sottomissione del form di registrazione e' pubblica.
				.requestMatchers(HttpMethod.POST, "/register").permitAll()

				// --- Riservate agli utenti autenticati ---
				.requestMatchers("/biglietti/**").authenticated()
				.requestMatchers("/recensioni/**").authenticated()

				// --- Riservate all'amministratore ---
				.requestMatchers("/admin/**").hasAnyAuthority("ADMIN")

				// --- Rete di sicurezza: tutto il resto richiede il login ---
				.anyRequest().authenticated()
			)
			// Form di login personalizzato (la pagina /login la serviremo noi
			// con un controller e un template Thymeleaf).
			.formLogin(form -> form
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
				.permitAll()
			)
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.permitAll()
			);

		return http.build();
	}
}
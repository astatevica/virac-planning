package lv.venta.virac.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
	
	private final JwtAuthFilter jwtAuthFilter; //Chat & Ali_Bouali
	//private final AuthenticationProvider authenticationProvider; //Ali_Bouali
	
	public SecurityConfiguration(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/**").hasRole("ROLE_ADMIN")
            .requestMatchers("/api/filter/plans/crud/{idDarbiniekam}").hasRole("ROLE_USER")
            .requestMatchers("/api/filter/plans/filter/{idDarbiniekam}/{gads}").hasRole("ROLE_USER")
            .requestMatchers("/api/filter/plans/filter/{idDarbiniekam}/{projekti}").hasRole("ROLE_USER")
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	
	//Chat
	@Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

	//Chat
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//Ali_Bouali
//		http
//			.csrf() //Chat
//			.disable() //Chat
//			.authorizeHttpRequests() //Chat
//			.requestMatchers("/api/auth/**") //Chat
//			.permitAll() //Chat
//			.anyRequest() //Chat
//			.authenticated() //Chat
//			.and()
//			.sessionManagement()
//			.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Chat
//			.and()
//			.authenticationProvider(authenticationProvider)
//			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); //Chat
//		
//		return http.build();
//	}
    
    

}

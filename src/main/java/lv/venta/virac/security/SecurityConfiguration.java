package lv.venta.virac.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
	
	private final JwtAuthFilter jwtAuthFilter; //ChatHPT & Ali_Bouali
	//private final AuthenticationProvider authenticationProvider; //Ali_Bouali
	
	public SecurityConfiguration(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//Ali_Bouali
//		http
//			.csrf() //ChatGPT
//			.disable()
//			.authorizeHttpRequests()
//			.requestMatchers("/api/auth/**")
//			.permitAll()
//			.anyRequest()
//			.authenticated()
//			.and()
//			.sessionManagement()
//			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			.and()
//			.authenticationProvider(authenticationProvider)
//			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//		
		
		http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers("/admin/api/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	

}

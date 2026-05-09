package lv.venta.virac.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
	
	private final JwtAuthFilter jwtAuthFilter;
	private final UserDetailsService userDetailsService;
	
	public SecurityConfiguration(JwtAuthFilter jwtAuthFilter, UserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                //PUBLIC ENDPOINTS
                .requestMatchers("/api/auth/**","/error").permitAll()
                .requestMatchers("/api/export/**","/error").permitAll()
                
                
                //USER & ADMIN & USER_DEPART
                .requestMatchers("/api/year/**").hasAnyRole("ADMIN","USER","USER_DEPART")
                
                //TEST ADMIN
                .requestMatchers("/api/admin/**").hasAnyRole("ADMIN")
                .requestMatchers("/api/admin/plan/{id}").hasRole("ADMIN")
                .requestMatchers("/api/admin/plan/all").hasRole("ADMIN")
                
                //USER_DEPART
                .requestMatchers("/api/admin/employee/filter/department").hasRole("USER_DEPART") //all employees by department
                .requestMatchers("/api/admin/plan/filter/employee/{idEmployee}").hasRole("USER_DEPART") //all plans for specific employee
                .requestMatchers("/api/admin/plan/{id}").hasRole("USER_DEPART") //specific employee plan
                .requestMatchers("/api/admin/plan/filter/department").hasRole("USER_DEPART") //all plans by department
                .requestMatchers("/api/admin/plan/filter/department/year/{idYear}").hasRole("USER_DEPART")//Filters plans by year and department  
                .requestMatchers("/api/admin/department/credentials").hasRole("USER_DEPART")//Credentials for Department
                
                //ADMIN
//                .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                .requestMatchers("/api/admin/plan/{id}").hasRole("ADMIN")
//                .requestMatchers("/api/admin/plan/all").hasRole("ADMIN")
                
                //USER
                .requestMatchers("/api/user/**").hasRole("USER")                            
                                            
                //EVERYTHING ELSE
                .requestMatchers("/api/auth/logout").authenticated()
                .anyRequest().authenticated()
                
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
	
	@Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}

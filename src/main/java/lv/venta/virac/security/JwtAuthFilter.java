package lv.venta.virac.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{
	
	//Chat & Ali_Bouali 
	private final JwtService jwtService;
	private UserDetailsService userDetailsService;
	
	//Chat
    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //Paņem tokenu no autentifikācijas
        final String jwt = authHeader.substring(7);
        //Izvelk username (epastu vai ko citu specifisku)
        final String username = jwtService.extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	//Iegūst info par lietotāju no DB
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            //Ja user ir valid, tad 
            if (jwtService.isTokenValid(jwt, userDetails)) {
            	//tad tiek izveidots user token
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                //tad tokenu apvieno ar detaļām, kuras mums nepieciešamas
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                //un atjauno tokenu
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        //jāizsauc filtrēšanas ķēde, lai tiek filtrēšana izdarīta
        filterChain.doFilter(request, response);
    }
    
//	@Override
//	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, 
//			@NonNull FilterChain filterChain)throws ServletException, IOException {
//		final String authHeader = request.getHeader("Authorization"); //BOTH
    	//BOTH
//		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
//			filterChain.doFilter(request, response);
//			return;
//		}
    	//BOTH
//		final String jwt;
//		final String userEmail;
//   	//BOTH
//		jwt = authHeader.substring(7);
//		userEmail = jwtService.extractUsername(jwt);
//		//BOTH
//		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//			//dabūjam info par lietotāju no DB BOTH
//	      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
    		//Ali-Bouali
//	      var isTokenValid = tokenRepository.findByToken(jwt)
//	          .map(t -> !t.isExpired() && !t.isRevoked())
//	          .orElse(false);
//	      //Ja user ir valid, tad BOTH
//	      if (jwtService.isTokenValid(jwt, userDetails) /*&& isTokenValid*/) {
//	    	  //tad tiek izveidots user token BOTH
//	        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//	            userDetails,
//	            null,
//	            userDetails.getAuthorities()
//	        );
//	        //tad tokenu apvieno ar detaļām, kuras mums nepieciešamas BOTH
//	        authToken.setDetails(
//	            new WebAuthenticationDetailsSource().buildDetails(request)
//	        );
//	        //un atjauno tokenu BOTH
//	        SecurityContextHolder.getContext().setAuthentication(authToken);
//	      }
//	      //jāizsauc filtrēšanas ķēde, lai tiek filtrēšana izdarīta BOTH
//	      filterChain.doFilter(request, response);
//	    }
//	}


}

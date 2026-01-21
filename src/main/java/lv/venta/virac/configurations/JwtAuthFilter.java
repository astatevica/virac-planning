package lv.venta.virac.configurations;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lv.venta.virac.service.JwtService;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{
	
	private final JwtService jwtService;
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request, 
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain
		)throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwt = authHeader.substring(7);
		userEmail = jwtService.extractUsername(jwt);
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//	      var isTokenValid = tokenRepository.findByToken(jwt)
//	          .map(t -> !t.isExpired() && !t.isRevoked())
//	          .orElse(false);
//	      if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
//	        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//	            userDetails,
//	            null,
//	            userDetails.getAuthorities()
//	        );
//	        authToken.setDetails(
//	            new WebAuthenticationDetailsSource().buildDetails(request)
//	        );
//	        SecurityContextHolder.getContext().setAuthentication(authToken);
//	      }
	    }
	}

}

package lv.venta.virac.service;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {
	
	public String extractUsername(String token) {
		return null;
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSihnInKey()).build().parseClaimsJws(token)
	}

}

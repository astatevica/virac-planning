package lv.venta.virac.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lv.venta.virac.auth.dto.AuthenticationRequest;
import lv.venta.virac.auth.dto.AuthenticationResponse;
import lv.venta.virac.security.JwtService;
import lv.venta.virac.token.RefreshTokenService;
import lv.venta.virac.user.IUserRepo;
import lv.venta.virac.user.User;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final IUserRepo userRepo;
	//private final PasswordEncoder passwordEncoder; 
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final RefreshTokenService refreshTokenService;
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow();

        String accessToken = jwtService.generateToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user.getId()).getToken();

        return new AuthenticationResponse(accessToken, refreshToken);
    }
	
//	public AuthenticationResponse register(RegisterRequest request) {
//		var user = User.builder()
//				.firstname(request.getFirstname())
//				.lastname(request.getLastname())
//				.email(request.getEmail())
//				.password(passwordEncoder.encode(request.getPassword()))
//				.role(Role.USER)
//				.build();
//		repository.save(user);
//		var jwtToken = jwtService.generateToken(user); 
//		return AuthenticationResponse.builder().build()
//				.token(jwtToken)
//				.build();
//	}
//	
//	public AuthenticationResponse authenticate(AuthenticationRequest request) {
//		authenticationManager.authenticate(
//			new UsernamePasswordAuthenticationToken(
//					request.getEmail(),
//					request.getPassword())
//			);
//		var user = repository.findByEmail(request.getEmail())
//				.orElseThrow();
//		
//		var jwtToken = jwtService.generateToken(user); 
//		return AuthenticationResponse.builder().build()
//				.token(jwtToken)
//				.build();
//	}
}

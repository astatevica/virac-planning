package lv.venta.virac.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lv.venta.virac.auth.dto.AuthenticationRequest;
import lv.venta.virac.auth.dto.AuthenticationResponse;
import lv.venta.virac.auth.dto.RegisterRequest;
import lv.venta.virac.repo.IEmployeeRepo;
import lv.venta.virac.security.JwtService;
import lv.venta.virac.token.RefreshToken;
import lv.venta.virac.token.RefreshTokenService;
import lv.venta.virac.user.IUserRepo;
import lv.venta.virac.user.Role;
import lv.venta.virac.user.User;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final IUserRepo userRepo;
	private final IEmployeeRepo employeeRepo;
	private final PasswordEncoder passwordEncoder; 
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final RefreshTokenService refreshTokenService;
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow() ;

        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        System.out.println(user.getRole().name());

        return new AuthenticationResponse(accessToken, refreshToken.getToken(), user.getRole().name());
    }
	
	public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.valueOf(request.getIdRole()))
				.employee(employeeRepo.findById(request.getIdEmployee()).get())
				.build();
		
		User new_user = userRepo.save(user);
		String accessToken = jwtService.generateToken(user); 
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
//		return AuthenticationResponse.builder().build()
//				.token(jwtToken)
//				.build();
		return new AuthenticationResponse(accessToken, refreshToken.getToken(), new_user.getRole().name());
	}
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

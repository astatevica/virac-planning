//package lv.venta.virac.auth;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import lombok.RequiredArgsConstructor;
//import lv.venta.virac.auth.dto.AuthenticationRequest;
//import lv.venta.virac.auth.dto.AuthenticationResponse;
//import lv.venta.virac.auth.dto.RegisterRequest;
//import lv.venta.virac.security.JwtService;
//import lv.venta.virac.user.IUserRepo;
//import lv.venta.virac.user.Role;
//
//@Service
//@RequiredArgsConstructor
//public class AuthenticationService {
//	
//	private final IUserRepo repository;
//	private final PasswordEncoder passwordEncoder; 
//	private final JwtService jwtService;
//	private final AuthenticationManager authenticationManager;
//	
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
//}

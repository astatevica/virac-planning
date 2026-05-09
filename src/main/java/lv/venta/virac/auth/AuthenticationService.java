package lv.venta.virac.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lv.venta.virac.auth.dto.AuthenticationRequest;
import lv.venta.virac.auth.dto.AuthenticationResponse;
import lv.venta.virac.security.JwtService;
import lv.venta.virac.token.RefreshToken;
import lv.venta.virac.token.RefreshTokenService;
import lv.venta.virac.user.IUserRepo;
import lv.venta.virac.user.User;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final IUserRepo userRepo;
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

}

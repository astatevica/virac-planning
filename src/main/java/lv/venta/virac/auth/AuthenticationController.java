package lv.venta.virac.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lv.venta.virac.auth.dto.AuthenticationRequest;
import lv.venta.virac.auth.dto.AuthenticationResponse;
import lv.venta.virac.security.JwtService;
import lv.venta.virac.token.IRefreshTokenRepo;
import lv.venta.virac.token.RefreshToken;
import lv.venta.virac.token.RefreshTokenService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {
	
	private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final IRefreshTokenRepo refreshTokenRepository;

    public AuthenticationController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            RefreshTokenService refreshTokenService,
            IRefreshTokenRepo refreshTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @PostMapping("/login")
    public AuthenticationResponse login(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateToken(user);

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(
                        Integer.valueOf(user.getUsername()) // adapt to your user ID logic
                );

        Cookie cookie = new Cookie("refreshToken", refreshToken.getToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        return new AuthenticationResponse(accessToken);
    }

    @PostMapping("/refresh")
    public AuthenticationResponse refresh(
            @CookieValue("refreshToken") String refreshToken) {

        RefreshToken token = refreshTokenRepository
                .findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow();

        String newAccessToken =
                jwtService.generateToken(
                        token.getUser()
                );

        return new AuthenticationResponse(newAccessToken);
    }

    @PostMapping("/logout")
    public void logout(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response) {

        refreshTokenRepository
                .findByToken(refreshToken)
                .ifPresent(refreshTokenRepository::delete);

        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/auth");
        response.addCookie(cookie);
    }
	
	
	//Ali-Bouali
//	private final AuthenticationService service;
//
//	@PostMapping("/register")
//	public ResponseEntity<AuthenticationResponse> register(
//			@RequestBody RegisterRequest){
//		return ResponseEntity.ok(service.register(request));
//	}
//	
//	@PostMapping("/authenticate")
//	public ResponseEntity<AuthenticationResponse> authenticate(
//			@RequestBody AuthenticationRequest){
//		return ResponseEntity.ok(service.authenticate(request));
//	}
	
}

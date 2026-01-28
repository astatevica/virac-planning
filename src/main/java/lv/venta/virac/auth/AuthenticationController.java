package lv.venta.virac.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lv.venta.virac.auth.dto.AuthenticationRequest;
import lv.venta.virac.auth.dto.AuthenticationResponse;
import lv.venta.virac.auth.dto.RegisterRequest;
import lv.venta.virac.token.RefreshTokenService;
import lv.venta.virac.user.User;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {
	
	private AuthenticationService authenticationService;
    private RefreshTokenService refreshTokenService;
    
    public AuthenticationController(AuthenticationService authenticationService,
            RefreshTokenService refreshTokenService) {
    			this.authenticationService = authenticationService;
    			this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) throws Exception {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(
            @RequestParam String refreshToken
    ) {
        return ResponseEntity.ok(refreshTokenService.refreshToken(refreshToken));
    }
    
//    @PostMapping("/logout")
//    public ResponseEntity<Void> logout(@RequestParam int userId) {
//        refreshTokenService.deleteByUserId(userId);
//        return ResponseEntity.ok().build();
//    }
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        refreshTokenService.deleteByUser(user);

        return ResponseEntity.ok().build();
    }

}

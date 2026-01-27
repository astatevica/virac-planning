package lv.venta.virac.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lv.venta.virac.auth.dto.AuthenticationRequest;
import lv.venta.virac.auth.dto.AuthenticationResponse;
import lv.venta.virac.auth.dto.RegisterRequest;
import lv.venta.virac.token.RefreshTokenService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {
	
	private AuthenticationService authenticationService;
    private RefreshTokenService refreshTokenService;

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
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam int userId) {
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok().build();
    }

}

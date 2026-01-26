package lv.venta.virac.token;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lv.venta.virac.auth.dto.AuthenticationResponse;
import lv.venta.virac.security.JwtService;
import lv.venta.virac.user.IUserRepo;
import lv.venta.virac.user.User;

@Service
public class RefreshTokenService {
	
    private long refreshExpirationMs = 604800000; //7 days

    private final IRefreshTokenRepo refreshTokenRepository;
    private final IUserRepo userRepository;
    private final JwtService jwtService;

    public RefreshTokenService(IRefreshTokenRepo refreshTokenRepository,IUserRepo userRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public RefreshToken createRefreshToken(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow();

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(
                Instant.now().plusMillis(refreshExpirationMs)
        );

        return refreshTokenRepository.save(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.isExpired()) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }
        return token;
    }
    
    public AuthenticationResponse refresh(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
        	refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        String newAccessToken = jwtService.generateToken(refreshToken.getUser());
        return new AuthenticationResponse(newAccessToken, token);
    }

}

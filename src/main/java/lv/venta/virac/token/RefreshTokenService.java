package lv.venta.virac.token;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lv.venta.virac.auth.dto.AuthenticationResponse;
import lv.venta.virac.exception.RefreshTokenException;
import lv.venta.virac.security.JwtService;
import lv.venta.virac.user.User;

@Service
public class RefreshTokenService {
	
    private static final long refreshExpirationMs = 604800000; //7 days

    private final IRefreshTokenRepo refreshTokenRepository;
    private final JwtService jwtService;

    public RefreshTokenService(IRefreshTokenRepo refreshTokenRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }
    
    //Paskatīties to user padošanu vai kā objektu vai kā int id
    public RefreshToken createRefreshToken(User user) {
        RefreshToken token = new RefreshToken();
	        token.setUser(user);
	        token.setToken(UUID.randomUUID().toString());
	        token.setExpiryDate(
	                Instant.now().plusMillis(refreshExpirationMs)
        );

        return refreshTokenRepository.save(token);
    }
    
    @Transactional
    public AuthenticationResponse refreshToken(String requestToken) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(requestToken)
                .orElseThrow(() -> new RefreshTokenException("Refresh token not found"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenException("Refresh token expired");
        }

        // Rotate refresh token
        refreshTokenRepository.delete(refreshToken);

        RefreshToken newRefreshToken = createRefreshToken(refreshToken.getUser());
        String newAccessToken = jwtService.generateToken(refreshToken.getUser());

        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken.getToken())
                .build();
    }

    @Transactional
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByIdUser(user.getId());
    }

    @Transactional
    public void deleteByUserId(int userId) {
        refreshTokenRepository.deleteByIdUser(userId);
    }

}

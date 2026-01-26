package lv.venta.virac.token;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lv.venta.virac.user.IUserRepo;
import lv.venta.virac.user.User;

@Service
public class RefreshTokenService {
	
    private long refreshExpirationMs = 604800000; //7 days

    private final IRefreshTokenRepo refreshTokenRepository;
    private final IUserRepo userRepository;

    public RefreshTokenService(IRefreshTokenRepo refreshTokenRepository,IUserRepo userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
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

}

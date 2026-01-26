package lv.venta.virac.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRefreshTokenRepo extends JpaRepository<RefreshToken, Integer>{

	Optional<RefreshToken> findByToken(String token);

    void deleteByUserId(int userId);
}

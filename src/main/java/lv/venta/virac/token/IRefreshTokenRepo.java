package lv.venta.virac.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRefreshTokenRepo extends JpaRepository<RefreshToken, Integer>{

	public abstract Optional<RefreshToken> findByToken(String token);

	public abstract void deleteByUser_IdUser(int idUser);
}

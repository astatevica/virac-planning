package lv.venta.virac.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

}
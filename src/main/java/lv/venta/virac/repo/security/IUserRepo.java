package lv.venta.virac.repo.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.venta.virac.model.security.User;

public interface IUserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

}
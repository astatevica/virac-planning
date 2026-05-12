package lv.venta.virac.repo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lv.venta.virac.model.Employee;
import lv.venta.virac.token.IRefreshTokenRepo;
import lv.venta.virac.token.RefreshToken;
import lv.venta.virac.user.IUserRepo;
import lv.venta.virac.user.Role;
import lv.venta.virac.user.User;

@DataJpaTest
public class IRefreshTokenRepoTest {
	
	@Autowired
    private IRefreshTokenRepo refreshTokenRepo;

    @Autowired
    private IUserRepo userRepo;
    
    private static User user;
    private static Employee employee;
    private static RefreshToken token;
    
    
    @BeforeAll
	static void setUp() {
    	employee = new Employee();
		user = new User("Janis","Berzins","janis@test.lv","123",Role.ADMIN, employee);
		token = new RefreshToken();
	}

    @Test
    void testFindByToken() {
        userRepo.save(user);
        token.setToken("abc123");
        token.setUser(user);
        token.setExpiryDate(Instant.now().plusSeconds(3600));

        refreshTokenRepo.save(token);

        Optional<RefreshToken> result = refreshTokenRepo.findByToken("abc123");

        assertTrue(result.isPresent());
        assertEquals("abc123", result.get().getToken());
    }

    @Test
    void testDeleteByUserId() {
        userRepo.save(user);
        token.setToken("delete-token");
        token.setUser(user);
        token.setExpiryDate(Instant.now().plusSeconds(3600));

        refreshTokenRepo.save(token);
        refreshTokenRepo.deleteByUser_IdUser(user.getIdUser());
        Optional<RefreshToken> result = refreshTokenRepo.findByToken("delete-token");

        assertFalse(result.isPresent());
    }

}

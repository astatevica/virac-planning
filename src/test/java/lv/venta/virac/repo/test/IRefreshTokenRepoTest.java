package lv.venta.virac.repo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import lv.venta.virac.model.Employee;
import lv.venta.virac.repo.IEmployeeRepo;
import lv.venta.virac.token.IRefreshTokenRepo;
import lv.venta.virac.token.RefreshToken;
import lv.venta.virac.user.IUserRepo;
import lv.venta.virac.user.Role;
import lv.venta.virac.user.User;

@DataJpaTest
@ActiveProfiles("test")
public class IRefreshTokenRepoTest {
	
	@Autowired
    private IRefreshTokenRepo refreshTokenRepo;
	
	@Autowired
    private IEmployeeRepo employeeRepo;

    @Autowired
    private IUserRepo userRepo;
    
    private User user;
    private Employee employee;
    private RefreshToken token;
    
    
    @BeforeEach
	void setUp() {
    	employee = new Employee();
		user = new User("Janis","Berzins","janis@test.lv","123",Role.ADMIN, employee);
		token = new RefreshToken();
		
		employee.setPosition("Test position");
		employeeRepo.save(employee);
		userRepo.save(user);
		token.setUser(user);
		token.setToken("abc123");
		token.setExpiryDate(Instant.now().plusSeconds(3600));
		refreshTokenRepo.save(token);
	}

    @Test
    void testFindByToken() {

        refreshTokenRepo.save(token);

        Optional<RefreshToken> result = refreshTokenRepo.findByToken("abc123");

        assertTrue(result.isPresent());
        assertEquals("abc123", result.get().getToken());
    }

    @Test
    void testDeleteByUserId() {
        token.setToken("delete-token");

        refreshTokenRepo.save(token);
        refreshTokenRepo.deleteByUser_IdUser(user.getIdUser());
        Optional<RefreshToken> result = refreshTokenRepo.findByToken("delete-token");

        assertFalse(result.isPresent());
    }

}

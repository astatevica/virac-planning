package lv.venta.virac.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import lv.venta.virac.model.Employee;
import lv.venta.virac.user.Role;
import lv.venta.virac.user.User;

public class UserTest {
	
	private static Employee defaultEmployee;
	private static User defaultUser;
	private static User goodUser;
	private static User nullUser;
	
	
	@BeforeAll
	static void setUp() {
		defaultEmployee = new Employee();
		defaultUser = new User();
		goodUser = new User("Janis","Berzins","janis@test.lv","password123",Role.ADMIN,defaultEmployee);
		nullUser = new User(null,null,null,null,null,null);
	}

    @Test
    void testConstructorAndGetters() {
        assertEquals("Janis", goodUser.getFirstname());
        assertEquals("Berzins", goodUser.getLastname());
        assertEquals("janis@test.lv", goodUser.getEmail());
        assertEquals("password123", goodUser.getPassword());
        assertEquals(Role.ADMIN, goodUser.getRole());
        assertEquals(defaultEmployee, goodUser.getEmployee());
    }
    
    @Test
	void testNullUser() {
    	assertNull(nullUser.getIdUser());
	}

    @Test
    void testGetAuthorities() {
    	//Gets List with authorities for specific user
        Collection<? extends GrantedAuthority> authorities = goodUser.getAuthorities();
        //Tests if there is only granted authority
        assertEquals(1, authorities.size());
        //Takes first Authority from list
        GrantedAuthority authority = authorities.iterator().next();
        //Verifies if this authority is equal to user authoority
        assertEquals("ROLE_ADMIN", authority.getAuthority());
    }

    @Test
    void testGetUsername() {
        assertEquals("janis@test.lv", goodUser.getUsername());
    }

    @Test
    void testIsEnabledWhenNotDeleted() {
        User user = User.builder().deleted(false).build();
        assertTrue(user.isEnabled());
    }

    @Test
    void testIsDisabledWhenDeleted() {
        User user = User.builder().deleted(true).build();
        assertFalse(user.isEnabled());
    }

    @Test
    void testAccountStatusMethods() {
        assertTrue(defaultUser.isAccountNonExpired());
        assertTrue(defaultUser.isAccountNonLocked());
        assertTrue(defaultUser.isCredentialsNonExpired());
    }
}
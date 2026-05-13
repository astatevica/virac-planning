package lv.venta.virac.service.impl.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.hibernate.Filter;
import org.hibernate.Session;

import jakarta.persistence.EntityManager;
import lv.venta.virac.model.Employee;
import lv.venta.virac.repo.IEmployeeRepo;
import lv.venta.virac.user.CRUDUserServiceImpl;
import lv.venta.virac.user.IUserRepo;
import lv.venta.virac.user.Role;
import lv.venta.virac.user.User;

@ExtendWith(MockitoExtension.class)
public class CRUDUserServiceImplTest {
	
	@Mock
    private IUserRepo userRepo;

    @Mock
    private IEmployeeRepo employeeRepo;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Session session;

    @Mock
    private Filter filter;
    
    @InjectMocks
    private CRUDUserServiceImpl userService;
    
    @Test
    void testRetrieveAll() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User());
        when(entityManager.unwrap(Session.class)).thenReturn(session);
        when(session.enableFilter("deletedUserFilter")).thenReturn(filter);
        when(userRepo.findAll()).thenReturn(users);
        ArrayList<User> result = userService.retrieveAll();
        assertEquals(1, result.size());
        verify(session).disableFilter("deletedUserFilter");
    }
    
    @Test
    void testRetrieveAllEmpty() {
        when(entityManager.unwrap(Session.class)).thenReturn(session);
        when(session.enableFilter("deletedUserFilter")).thenReturn(filter);
        when(userRepo.findAll()).thenReturn(new ArrayList<>());
        assertThrows(Exception.class,() -> userService.retrieveAll());
    }
    
    @Test
    void testRetrieveById() throws Exception {
        User user = new User();
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        User result = userService.retrieveById(1);
        assertNotNull(result);
    }
    
    @Test
    void testRetrieveByIdInvalidId() {
        assertThrows(Exception.class,() -> userService.retrieveById(0));
    }
    
    @Test
    void testDeleteById() throws Exception {
        User user = new User();
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        userService.deleteById(1);
        assertTrue(user.isDeleted());
        verify(userRepo).save(user);
    }
    
    @Test
    void testUpdateById() throws Exception {
        User user = new User();
        Employee emp = new Employee();
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(employeeRepo.findById(1)).thenReturn(Optional.of(emp));
        userService.updateById(
                1,
                "Janis",
                "Berzins",
                "janis@test.lv",
                "123",
                "ADMIN",
                1
        );
        assertEquals("Janis",user.getFirstname());
        assertEquals(Role.ADMIN,user.getRole());
        verify(userRepo).save(user);
    }
    
    @Test
    void testUpdateEmployeeNotFound() {
        User user = new User();
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(employeeRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(Exception.class,
                () -> userService.updateById(
                        1,
                        "A",
                        "B",
                        "a@test.lv",
                        "123",
                        "ADMIN",
                        1
                ));
    }

}

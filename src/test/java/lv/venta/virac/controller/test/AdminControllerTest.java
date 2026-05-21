package lv.venta.virac.controller.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import lv.venta.virac.auth.AuthenticationService;
import lv.venta.virac.auth.dto.RegisterRequest;
import lv.venta.virac.controller.AdminController;
import lv.venta.virac.model.Employee;
import lv.venta.virac.scheduler.ICRUDPlanSchedulerService;
import lv.venta.virac.scheduler.SchedulerDTO;
import lv.venta.virac.user.ICRUDUserService;
import lv.venta.virac.user.IUserRepo;
import lv.venta.virac.user.Role;
import lv.venta.virac.user.User;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AdminControllerTest {
	
	@Mock
    private AuthenticationService authenticationService;

    @Mock
    private ICRUDUserService userService;

    @Mock
    private ICRUDPlanSchedulerService schedulerService;

    @InjectMocks
    private AdminController controller;
    
    @Autowired
    private IUserRepo userRepo;

    private User user;

    @BeforeEach
    void setUp() {

        Employee employee = new Employee();

        user = new User();
        user.setFirstname("Janis");
        user.setLastname("Berzins");
        user.setEmail("janis@test.lv");
        user.setPassword("123");
        user.setRole(Role.ADMIN);
        user.setEmployee(employee);
        
    }

    @Test
    void testAdminDashboard() {

        String result = controller.adminDashboard();

        assertEquals("Only ADMIN can see this", result);
    }

    @Test
    void testCreateUser() {

        RegisterRequest request = new RegisterRequest(1,"Janis","Berzins","janis@test.lv","123","ADMIN",1);

        ResponseEntity<?> response = controller.createUser(request);

        assertEquals(200, response.getStatusCode().value());

        verify(authenticationService).createUserByAdmin(request);
    }

    @Test
    void testDeleteById() throws Exception {

        ResponseEntity<Void> response = controller.deleteById(1);

        assertEquals(204, response.getStatusCode().value());

        verify(userService).deleteById(1);
    }

    @Test
    void testGetSchedulerByYear() throws Exception {

        SchedulerDTO dto = new SchedulerDTO();
        dto.setIdYear(1);

        when(schedulerService.getByYearId(1)).thenReturn(dto);

        ResponseEntity<SchedulerDTO> response = controller.getSchedulerByYear(1, null);

        assertEquals(1, response.getBody().getIdYear());
    }

    @Test
    void testUpdateScheduler() throws Exception {

        SchedulerDTO dto = new SchedulerDTO();
        dto.setIdYear(1);

        ResponseEntity<?> response = controller.updateSchedulerByYear(dto, new org.springframework.validation.BeanPropertyBindingResult(dto, "dto"));

        assertEquals(200, response.getStatusCode().value());

        verify(schedulerService).update(dto);
    }
}



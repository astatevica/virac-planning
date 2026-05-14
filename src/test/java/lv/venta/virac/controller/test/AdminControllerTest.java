package lv.venta.virac.controller.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import lv.venta.virac.auth.AuthenticationService;
import lv.venta.virac.auth.dto.RegisterRequest;
import lv.venta.virac.controller.AdminController;
import lv.venta.virac.model.Employee;
import lv.venta.virac.scheduler.ICRUDPlanSchedulerService;
import lv.venta.virac.scheduler.SchedulerDTO;
import lv.venta.virac.security.JwtAuthFilter;
import lv.venta.virac.security.JwtService;
import lv.venta.virac.user.ICRUDUserService;
import lv.venta.virac.user.Role;
import lv.venta.virac.user.User;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=none"
})
public class AdminControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AuthenticationService authenticationService;

	@MockitoBean
	private ICRUDUserService userService;

	@MockitoBean
	private ICRUDPlanSchedulerService schedulerService;
	
	@MockitoBean
	private JwtService jwtService;

	@MockitoBean
	private JwtAuthFilter jwtAuthFilter;
	
	private static User user;
	private static Employee employee;
	
	@BeforeEach
	void setUp() {

	    employee = new Employee();

	    ReflectionTestUtils.setField(employee,"idEmployee",1);

	    user = new User();

	    ReflectionTestUtils.setField(user,"idUser",1);

	    user.setFirstname("Janis");
	    user.setLastname("Berzins");
	    user.setEmail("janis@test.lv");
	    user.setPassword("123");
	    user.setRole(Role.ADMIN);
	    user.setEmployee(employee);

	    //RegisterRequest request = new RegisterRequest(1,"Janis","Berzins","janis@test.lv","123","ADMIN",1);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void testAdminDashboard() throws Exception {

	    mockMvc.perform(get("/api/admin/dashboard"))
	            .andExpect(status().isOk())
	            .andExpect(content().string(
	                    "Only ADMIN can see this"
	            ));
	}
	
	@Test
	@WithMockUser(roles = "USER")
	void testAdminDashboardForbidden()
	        throws Exception {

	    mockMvc.perform(get("/api/admin/dashboard"))
	            .andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void testCreateUser() throws Exception {

	    mockMvc.perform(
	            post("/api/admin/create-user")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("""
	                {
	                    "firstname":"Janis",
	                    "lastname":"Berzins",
	                    "email":"janis@test.lv",
	                    "password":"123",
	                    "role":"ADMIN",
	                    "idEmployee":1
	                }
	            """)
	    )
	    .andExpect(status().isOk());

	    verify(authenticationService)
	            .createUserByAdmin(any());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void testGetAllUsers() throws Exception {

	    ArrayList<User> users =
	            new ArrayList<>();

	    users.add(user);

	    when(userService.retrieveAll())
	            .thenReturn(users);

	    mockMvc.perform(get("/api/admin/all-users"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[0].firstname")
	                    .value("Janis"));
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void testGetById() throws Exception {

	    when(userService.retrieveById(1))
	            .thenReturn(user);

	    mockMvc.perform(get("/api/admin/1"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.email")
	                    .value("janis@test.lv"));
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void testUpdateById() throws Exception {

	    mockMvc.perform(
	            put("/api/admin/update/1")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("""
	                {
	                    "firstname":"Anna",
	                    "lastname":"Ozola",
	                    "email":"anna@test.lv",
	                    "password":"123",
	                    "role":"ADMIN",
	                    "idEmployee":1
	                }
	            """)
	    )
	    .andExpect(status().isOk());

	    verify(userService)
	            .updateById(
	                    anyInt(),
	                    anyString(),
	                    anyString(),
	                    anyString(),
	                    anyString(),
	                    anyString(),
	                    anyInt()
	            );
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void testDeleteById() throws Exception {

	    mockMvc.perform(
	            delete("/api/admin/delete/1")
	    )
	    .andExpect(status().isNoContent());

	    verify(userService)
	            .deleteById(1);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void testGetSchedulerByYear()
	        throws Exception {

	    SchedulerDTO dto =
	            new SchedulerDTO();

	    dto.setIdYear(1);

	    when(schedulerService.getByYearId(1))
	            .thenReturn(dto);

	    mockMvc.perform(
	            get("/api/admin/scheduler/1")
	    )
	    .andExpect(status().isOk())
	    .andExpect(jsonPath("$.idYear")
	            .value(1));
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void testUpdateScheduler()
	        throws Exception {

	    mockMvc.perform(
	            put("/api/admin/update/scheduler")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("""
	                {
	                    "idYear":1
	                }
	            """)
	    )
	    .andExpect(status().isOk());

	    verify(schedulerService)
	            .update(any());
	}
	
}



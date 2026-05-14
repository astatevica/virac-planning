package lv.venta.virac.controller.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.extension.ExtendWith;

import lv.venta.virac.user.User;
import lv.venta.virac.user.Role;
import lv.venta.virac.model.Employee;
import lv.venta.virac.model.ViracDepartment;
import lv.venta.virac.service.ICRUDEmployeeService;
import lv.venta.virac.service.ICRUDPlanService;
import lv.venta.virac.dto.FullPlanDTO;
import lv.venta.virac.email.EmailSendingService;
import lv.venta.virac.export.ExportController;
import lv.venta.virac.export.PlanExportService;

@WebMvcTest(ExportController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ExportControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private PlanExportService exportService;

	@MockitoBean
	private ICRUDPlanService planService;

	@MockitoBean
	private ICRUDEmployeeService employeeService;

	@MockitoBean
	private EmailSendingService emailService;
	
	private static User user;
	private static Employee employee;
	private static ViracDepartment dept;
	private static FullPlanDTO dto;
	
	@BeforeEach
	void setUp() {

	    dept = new ViracDepartment();
	    ReflectionTestUtils.setField(dept, "idDepartment", 1);

	    employee = new Employee();
	    ReflectionTestUtils.setField(employee, "idEmployee", 1);
	    employee.setViracDepartment(dept);

	    user = new User();
	    user.setRole(Role.ADMIN);
	    user.setEmployee(employee);
	    user.setEmail("test@test.lv");

	    dto = new FullPlanDTO();
	    dto.setIdEmployee(1);
	}
	
	private Authentication auth() {
	    return new UsernamePasswordAuthenticationToken(
	            user,
	            null,
	            user.getAuthorities()
	    );
	}
	
	@Test
	void exportDocx_success() throws Exception {

	    byte[] file = "test-file".getBytes();

	    when(planService.retrieveFullPlan(1))
	            .thenReturn(dto);

	    when(employeeService.retrieveById(1))
	            .thenReturn(employee);

	    when(exportService.generateDocx(dto))
	            .thenReturn(file);

	    mockMvc.perform(get("/api/export/docx/1")
	            .principal(auth()))
	            .andExpect(status().isOk())
	            .andExpect(header().exists(HttpHeaders.CONTENT_DISPOSITION))
	            .andExpect(content().bytes(file));

	    verify(exportService).generateDocx(dto);
	    verify(emailService).sendDocxEmailNotification(
	            anyString(),
	            anyString(),
	            anyString(),
	            anyString(),
	            any(byte[].class)
	    );
	}
	
	@Test
	void exportDocx_forbidden() throws Exception {

	    User other = new User();
	    other.setRole(Role.USER);
	    other.setEmployee(employee);

	    when(planService.retrieveFullPlan(1)).thenReturn(dto);
	    when(employeeService.retrieveById(1)).thenReturn(employee);

	    mockMvc.perform(get("/api/export/docx/1")
	            .principal(new UsernamePasswordAuthenticationToken(other, null, other.getAuthorities())))
	            .andExpect(status().isInternalServerError());
	}
	
	@Test
	void exportDocx_exception() throws Exception {

	    when(planService.retrieveFullPlan(1))
	            .thenThrow(new RuntimeException("fail"));

	    mockMvc.perform(get("/api/export/docx/1")
	            .principal(auth()))
	            .andExpect(status().isInternalServerError());
	}
	
	

}

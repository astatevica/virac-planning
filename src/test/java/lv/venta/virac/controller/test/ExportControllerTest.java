package lv.venta.virac.controller.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.test.context.ActiveProfiles;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import lv.venta.virac.user.User;
import lv.venta.virac.user.Role;
import lv.venta.virac.model.Employee;
import lv.venta.virac.model.ViracDepartment;
import lv.venta.virac.service.ICRUDEmployeeService;
import lv.venta.virac.service.ICRUDPlanService;
import lv.venta.virac.dto.FullPlanDTO;
import lv.venta.virac.export.ExportController;
import lv.venta.virac.export.PlanExportService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ExportControllerTest {
	
	@Mock
    private PlanExportService exportService;

    @Mock
    private ICRUDPlanService planService;

    @Mock
    private ICRUDEmployeeService employeeService;

    @Mock
    private lv.venta.virac.email.EmailSendingService emailService;

    @InjectMocks
    private ExportController controller;

    private User user;
    private Employee employee;
    private ViracDepartment dept;
    private FullPlanDTO dto;

    @BeforeEach
    void setUp() {

        dept = new ViracDepartment();

        employee = new Employee();
        employee.setViracDepartment(dept);

        user = new User();
        user.setRole(Role.ADMIN);
        user.setEmployee(employee);
        user.setEmail("test@test.lv");

        dto = new FullPlanDTO();
        dto.setIdEmployee(1);
    }
	
    @Test
    void exportDocx_success() throws Exception {

        byte[] file = "test-file".getBytes();

        when(planService.retrieveFullPlan(1)).thenReturn(dto);
        when(employeeService.retrieveById(1)).thenReturn(employee);
        when(exportService.generateDocx(dto)).thenReturn(file);

        Principal principal = () -> user.getEmail();

        ResponseEntity<byte[]> response =
                controller.exportDocx(1, new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities()
                ));

        assertEquals(200, response.getStatusCode().value());
        assertArrayEquals(file, response.getBody());
        assertEquals(true,response.getHeaders().containsKey(HttpHeaders.CONTENT_DISPOSITION));

        verify(exportService).generateDocx(dto);
        verify(emailService).sendDocxEmailNotification(
                any(),
                anyString(),
                anyString(),
                anyString(),
                any(byte[].class)
        );
    }

    @Test
    void exportDocx_exception() throws Exception {

        when(planService.retrieveFullPlan(1)).thenThrow(new RuntimeException("fail"));

        ResponseEntity<byte[]> response =
                controller.exportDocx(1,
                        new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                                user, null, user.getAuthorities()
                        ));

        assertEquals(500, response.getStatusCode().value());
    }

    @Test
    void exportDocx_forbidden_logic() throws Exception {

        User other = new User();
        other.setRole(Role.USER);
        other.setEmployee(employee);

        FullPlanDTO otherDto = new FullPlanDTO();
        otherDto.setIdEmployee(1);

        when(planService.retrieveFullPlan(1)).thenReturn(otherDto);
        when(employeeService.retrieveById(1)).thenReturn(employee);

        ResponseEntity<byte[]> response =
                controller.exportDocx(1,
                        new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                                other, null, other.getAuthorities()
                        ));

        assertEquals(500, response.getStatusCode().value());
    }
	

}

package lv.venta.virac.controller.test;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import lv.venta.virac.controller.CRUDProjManagController;
import lv.venta.virac.dto.ProjectManagementDTO;
import lv.venta.virac.model.Employee;
import lv.venta.virac.model.ProjectManagement;
import lv.venta.virac.service.ICRUDProjManagService;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CRUDProjManagControllerTest {
	
	@Mock
    private ICRUDProjManagService projService;

    @InjectMocks
    private CRUDProjManagController controller;

    private ProjectManagement pm;
    private Employee employee;

    @BeforeEach
    void setUp() {

    	employee = new Employee();

        pm = new ProjectManagement();
        pm.setEmployee(employee);
        pm.setStartDate(LocalDate.of(2026, 1, 1));
        pm.setEndDate(LocalDate.of(2026, 12, 31));
    }

    @Test
    void testGetAll() throws Exception {

    	ArrayList<ProjectManagement> list = new ArrayList<>();
        list.add(pm);

        when(projService.retrieveAll()).thenReturn(list);

        ResponseEntity<ArrayList<ProjectManagementDTO>> response = controller.getAll();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(0, response.getBody().get(0).getEmployeeId());
    }

    @Test
    void testGetById() throws Exception {

    	when(projService.retrieveById(1)).thenReturn(pm);

        ResponseEntity<ProjectManagementDTO> response = controller.getById(1);

        assertEquals(0, response.getBody().getIdProjectManag());
    }

    @Test
    void testCreate() throws Exception {

    	ProjectManagementDTO dto = new ProjectManagementDTO(
                0,
                1,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 31)
        );

        ResponseEntity<Void> response = controller.create(dto);

        assertEquals(201, response.getStatusCode().value());

        verify(projService).create(
                1,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 31)
        );
    }

    @Test
    void testUpdate() throws Exception {

    	ProjectManagementDTO dto = new ProjectManagementDTO(
                0,
                1,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 31)
        );

        ResponseEntity<Void> response = controller.update(1, dto);

        assertEquals(200, response.getStatusCode().value());

        verify(projService).updateById(
                1,
                1,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 31)
        );
    }

    @Test
    void testDelete() throws Exception {

    	ResponseEntity<Void> response = controller.delete(1);

        assertEquals(204, response.getStatusCode().value());

        verify(projService).deleteById(1);
    }

    @Test
    void testFilterByEmployee() throws Exception {

    	ArrayList<ProjectManagement> list = new ArrayList<>();
        list.add(pm);

        when(projService.selectAllProjectManagemetByEmployee(1)).thenReturn(list);

        ResponseEntity<ArrayList<ProjectManagementDTO>> response = controller.filterByEmployee(1);

        assertEquals(0, response.getBody().get(0).getEmployeeId());
    }

}

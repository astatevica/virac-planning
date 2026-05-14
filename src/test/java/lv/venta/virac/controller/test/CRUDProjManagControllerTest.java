package lv.venta.virac.controller.test;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.web.servlet.MockMvc;

import lv.venta.virac.controller.CRUDProjManagController;
import lv.venta.virac.dto.ProjectManagementDTO;
import lv.venta.virac.model.Employee;
import lv.venta.virac.model.ProjectManagement;
import lv.venta.virac.service.ICRUDProjManagService;


@WebMvcTest(CRUDProjManagController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class CRUDProjManagControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ICRUDProjManagService projService;

    private ProjectManagement pm;
    private Employee employee;

    @BeforeEach
    void setUp() {

        employee = new Employee();
        employee.setIdEmployee(1);

        pm = new ProjectManagement();
        pm.setIdProjectManag(1);
        pm.setEmployee(employee);
        pm.setStartDate(LocalDate.of(2026, 1, 1));
        pm.setEndDate(LocalDate.of(2026, 12, 31));
    }

    @Test
    void testGetAll() throws Exception {

        ArrayList<ProjectManagement> list = new ArrayList<>();
        list.add(pm);

        when(projService.retrieveAll()).thenReturn(list);

        mockMvc.perform(get("/api/admin/project-management/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId").value(1));
    }

    @Test
    void testGetById() throws Exception {

        when(projService.retrieveById(1)).thenReturn(pm);

        mockMvc.perform(get("/api/admin/project-management/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProjectManag").value(1));
    }

    @Test
    void testCreate() throws Exception {

        mockMvc.perform(post("/api/admin/project-management/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "employeeId":1,
                        "startDate":"2026-01-01",
                        "endDate":"2026-12-31"
                    }
                """))
                .andExpect(status().isCreated());

        verify(projService).create(
                1,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 31)
        );
    }

    @Test
    void testUpdate() throws Exception {

        mockMvc.perform(put("/api/admin/project-management/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "employeeId":1,
                        "startDate":"2026-01-01",
                        "endDate":"2026-12-31"
                    }
                """))
                .andExpect(status().isOk());

        verify(projService).updateById(
                1,
                1,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 31)
        );
    }

    @Test
    void testDelete() throws Exception {

        mockMvc.perform(delete("/api/admin/project-management/delete/1"))
                .andExpect(status().isNoContent());

        verify(projService).deleteById(1);
    }

    @Test
    void testFilterByEmployee() throws Exception {

        ArrayList<ProjectManagement> list = new ArrayList<>();
        list.add(pm);

        when(projService.selectAllProjectManagemetByEmployee(1))
                .thenReturn(list);

        mockMvc.perform(get("/api/admin/project-management/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId").value(1));
    }

}

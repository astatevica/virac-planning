package lv.venta.virac.controller.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import lv.venta.virac.controller.CRUDYearController;
import lv.venta.virac.model.Year;
import lv.venta.virac.security.JwtAuthFilter;
import lv.venta.virac.security.JwtService;
import lv.venta.virac.service.ICRUDYearService;

@WebMvcTest(CRUDYearController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class
})
public class CRUDYearControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ICRUDYearService yearService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private JwtAuthFilter jwtAuthFilter;

    private Year year;

    @BeforeEach
    void setUp() {

        year = new Year();

        year.setYearNumber(2026);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAll() throws Exception {

        ArrayList<Year> years = new ArrayList<>();

        years.add(year);

        when(yearService.retrieveAll()).thenReturn(years);

        mockMvc.perform(get("/api/year/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].yearNumber")
                        .value(2026));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetById() throws Exception {

        when(yearService.retrieveById(1)).thenReturn(year);

        mockMvc.perform(get("/api/year/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.yearNumber")
                        .value(2026));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreate() throws Exception {

        mockMvc.perform(
                post("/api/year/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "yearNumber":2026
                    }
                """)
        )
        .andExpect(status().isCreated());

        verify(yearService)
                .create(2026);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdate() throws Exception {

        mockMvc.perform(
                put("/api/year/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "yearNumber":2027
                    }
                """)
        )
        .andExpect(status().isOk());

        verify(yearService)
                .updateById(1, 2027);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDelete() throws Exception {

        mockMvc.perform(
                delete("/api/year/delete/1")
        )
        .andExpect(status().isNoContent());

        verify(yearService)
                .deleteById(1);
    }

}

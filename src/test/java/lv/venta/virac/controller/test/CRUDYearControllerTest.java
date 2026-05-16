package lv.venta.virac.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.http.ResponseEntity;

import lv.venta.virac.controller.CRUDYearController;
import lv.venta.virac.dto.YearDTO;
import lv.venta.virac.model.Year;
import lv.venta.virac.service.ICRUDYearService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CRUDYearControllerTest {

    @Mock
    private ICRUDYearService yearService;
    
    @InjectMocks
    private CRUDYearController controller;

    private Year year;

    @BeforeEach
    void setUp() {
        year = new Year();
        year.setYearNumber(2026);
    }

    @Test
    void testGetAll() throws Exception {

        ArrayList<Year> years = new ArrayList<>();
        years.add(year);

        when(yearService.retrieveAll()).thenReturn(years);

        ResponseEntity<ArrayList<YearDTO>> response = controller.getAll();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2026, response.getBody().get(0).getYearNumber());
    }

    @Test
    void testGetById() throws Exception {

        when(yearService.retrieveById(1)).thenReturn(year);
        ResponseEntity<YearDTO> response = controller.getById(1);
        assertEquals(2026, response.getBody().getYearNumber());
    }

    @Test
    void testCreate() throws Exception {

    	YearDTO dto = new YearDTO();
        dto.setYearNumber(2026);

        ResponseEntity<Void> response = controller.create(dto, new org.springframework.validation.BeanPropertyBindingResult(dto, "dto"));

        assertEquals(201, response.getStatusCode().value());
        verify(yearService).create(2026);
    }

    @Test
    void testUpdate() throws Exception {

        YearDTO dto = new YearDTO();
        dto.setYearNumber(2027);

        BindingResult bindingResult = new BeanPropertyBindingResult(dto, "dto");
        ResponseEntity<Void> response = controller.update(1, dto, bindingResult);
        assertEquals(200, response.getStatusCode().value());
        verify(yearService).updateById(1, 2027);
    }
    
    @Test
    void testDelete() throws Exception {
        ResponseEntity<Void> response = controller.delete(1);

        assertEquals(204, response.getStatusCode().value());
        verify(yearService).deleteById(1);
    }

}

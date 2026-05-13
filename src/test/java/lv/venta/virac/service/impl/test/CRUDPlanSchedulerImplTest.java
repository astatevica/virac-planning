package lv.venta.virac.service.impl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import jakarta.persistence.EntityManager;
import lv.venta.virac.model.Year;
import lv.venta.virac.repo.IYearRepo;
import lv.venta.virac.scheduler.CRUDPlanSchedulerImpl;
import lv.venta.virac.scheduler.IPlanScheduleRepo;
import lv.venta.virac.scheduler.PlanSchedule;
import lv.venta.virac.scheduler.SchedulerDTO;

@ExtendWith(MockitoExtension.class)
public class CRUDPlanSchedulerImplTest {
	
	@Mock
	private IPlanScheduleRepo scheduleRepo;

	@Mock
	private IYearRepo yearRepo;

	@Mock
	private EntityManager entityManager;

	@Mock
	private Session session;

	@Mock
	private Filter filter;

	@Mock
	private ModelMapper modelMapper;
	
	@InjectMocks
    private CRUDPlanSchedulerImpl planService;
	
	@Test
	void testRetrieveAll() throws Exception {

	    ArrayList<PlanSchedule> list = new ArrayList<>();

	    list.add(new PlanSchedule());

	    when(entityManager.unwrap(Session.class)).thenReturn(session);

	    when(session.enableFilter(anyString())).thenReturn(filter);

	    when(scheduleRepo.findAll()).thenReturn(list);

	    assertEquals(1,planService.retrieveAll().size());
	}
	
	@Test
	void testRetrieveAllEmpty() {

	    when(entityManager.unwrap(Session.class)).thenReturn(session);

	    when(session.enableFilter(anyString())).thenReturn(filter);

	    when(scheduleRepo.findAll()).thenReturn(new ArrayList<>());

	    assertThrows(Exception.class,() -> planService.retrieveAll());
	}
	
	@Test
	void testRetrieveById() throws Exception {

	    PlanSchedule ps = new PlanSchedule();

	    when(scheduleRepo.findById(1)).thenReturn(Optional.of(ps));

	    assertNotNull(planService.retrieveById(1)
	    );
	}
	
	@Test
	void testRetrieveByIdInvalid() {
	    assertThrows(Exception.class,() -> planService.retrieveById(0));
	}
	
	@Test
	void testDeleteById() throws Exception {

	    PlanSchedule ps = new PlanSchedule();

	    when(scheduleRepo.findById(1)).thenReturn(Optional.of(ps));

	    planService.deleteById(1);

	    assertTrue(ps.isDeleted());

	    verify(scheduleRepo).save(ps);
	}
	
	@Test
	void testCreate() throws Exception {

	    SchedulerDTO dto = new SchedulerDTO();

	    dto.setIdYear(1);

	    Year year = new Year();

	    when(scheduleRepo.findAll()).thenReturn(new ArrayList<>());

	    when(yearRepo.findById(1)).thenReturn(Optional.of(year));

	    planService.create(dto);

	    verify(scheduleRepo).save(any(PlanSchedule.class));
	}
	
	@Test
	void testCreateInvalidYear() {

	    SchedulerDTO dto = new SchedulerDTO();

	    dto.setIdYear(0);

	    assertThrows(Exception.class,() -> planService.create(dto));
	}

	@Test
	void testUpdate() throws Exception {

	    SchedulerDTO dto = new SchedulerDTO();

	    dto.setIdYear(1);

	    PlanSchedule ps = new PlanSchedule();

	    Year year = new Year();

	    when(scheduleRepo.findByYear_IdYear(1)).thenReturn(ps);

	    when(yearRepo.findById(1)).thenReturn(Optional.of(year));

	    planService.update(dto);

	    verify(scheduleRepo).save(ps);
	}
	
	@Test
	void testUpdateNotFound() {

	    SchedulerDTO dto = new SchedulerDTO();

	    dto.setIdYear(1);

	    when(scheduleRepo.findByYear_IdYear(1)).thenReturn(null);

	    assertThrows(Exception.class,() -> planService.update(dto));
	}
	
	@Test
	void testGetByYearId() throws Exception {

	    Year year = new Year();
	    year.setYearNumber(2026);

	    PlanSchedule ps = new PlanSchedule();
	    ps.setYear(year);

	    SchedulerDTO dto = new SchedulerDTO();
	    dto.setIdYear(year.getIdYear()); 

	    when(scheduleRepo.findByYear_IdYear(1)).thenReturn(ps);

	    when(modelMapper.map(ps, SchedulerDTO.class)).thenReturn(dto);

	    SchedulerDTO result = planService.getByYearId(1);

	    assertNotNull(result);
	    assertEquals(year.getIdYear(), result.getIdYear());
	}
	
	@Test
	void testGetByYearIdInvalid() {

	    assertThrows(Exception.class,() -> planService.getByYearId(0));
	}
	
}

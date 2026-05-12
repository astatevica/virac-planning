package lv.venta.virac.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lv.venta.virac.model.Year;
import lv.venta.virac.scheduler.PlanSchedule;

public class PlanScheduleTest {
	
	private static Year defaultYear;
	private static PlanSchedule schedule;
	private static PlanSchedule nullSchedule;
	private static LocalDate planned;
	private static LocalDate done;
	private static LocalDate date;
	
	@BeforeAll
	static void setUp() {
		defaultYear = new Year();
		planned = LocalDate.of(2026, 5, 12);
		done = LocalDate.of(2026, 5, 15);
		date = LocalDate.of(2026, 1, 1);
		schedule = new PlanSchedule(defaultYear, planned, done);
		nullSchedule = new PlanSchedule(null, null, null);
	}
	
	@Test
    void testConstructorAndGetters() {
        assertEquals(defaultYear, schedule.getYear());
        assertEquals(planned, schedule.getPlannedFreezeDate());
        assertEquals(done, schedule.getDoneFreezeDate());
    }
	
	@Test
	void testDeletedDefaultValue() {
	    assertFalse(schedule.isDeleted());
	}
	
	@Test
	void testSetters() {

	    schedule.setYear(defaultYear);
	    schedule.setPlannedFreezeDate(date);
	    schedule.setDoneFreezeDate(date);

	    assertEquals(defaultYear, schedule.getYear());
	    assertEquals(date, schedule.getPlannedFreezeDate());
	    assertEquals(date, schedule.getDoneFreezeDate());
	}
	
	@Test
	void testNullValues() {
	    assertNull(nullSchedule.getYear());
	    assertNull(nullSchedule.getPlannedFreezeDate());
	    assertNull(nullSchedule.getDoneFreezeDate());
	}
}

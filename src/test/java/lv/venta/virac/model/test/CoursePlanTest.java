package lv.venta.virac.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lv.venta.virac.model.Course;
import lv.venta.virac.model.CoursePlan;
import lv.venta.virac.model.Plan;

public class CoursePlanTest {
	
	private static Validator validator;
	private static Plan plan;
	private static Course course;
	private static CoursePlan cp;
	private static CoursePlan defaultCp;

	@BeforeAll
	static void setup() {
	    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    validator = factory.getValidator();
	    
	    plan = new Plan();
	    course = new Course();
	    cp = new CoursePlan(plan, course, "Done work");
	    defaultCp = new CoursePlan();
	}
	
	@Test
	void testConstructor() {
	    assertEquals(plan, cp.getPlan());
	    assertEquals(course, cp.getCourse());
	    assertEquals("Done work", cp.getWorkDone());
	}
	
	@Test
	void testDefaultValues() {
	    assertTrue(defaultCp.isDeleted());
	}
	
	@Test
	void testSetters() {
	    defaultCp.setPlan(plan);
	    defaultCp.setCourse(course);
	    defaultCp.setWorkDone("Testing work");

	    assertEquals(plan, defaultCp.getPlan());
	    assertEquals(course, defaultCp.getCourse());
	    assertEquals("Testing work", defaultCp.getWorkDone());
	}
	
	@Test
	void testWorkDoneTooShort() {
	    //CoursePlan cp = new CoursePlan();
		defaultCp.setWorkDone("ab");
	    Set<ConstraintViolation<CoursePlan>> result = validator.validate(defaultCp);
	    assertFalse(result.isEmpty());
	}
	
	@Test
	void testWorkDoneValid() {
	    //CoursePlan cp = new CoursePlan();
		defaultCp.setWorkDone("abc");
	    Set<ConstraintViolation<CoursePlan>> result = validator.validate(defaultCp);
	    assertTrue(result.isEmpty());
	}
	
	@Test
	void testDeletedFlag() {
	    //CoursePlan cp = new CoursePlan();
	    assertFalse(defaultCp.isDeleted());
	    defaultCp.setDeleted(true);
	    assertTrue(defaultCp.isDeleted());
	}
	
	@Test
	void testRelations() {

	    //CoursePlan cp = new CoursePlan();
		defaultCp.setPlan(plan);
		defaultCp.setCourse(course);

	    assertNotNull(defaultCp.getPlan());
	    assertNotNull(defaultCp.getCourse());
	}
	
	@Test
	void testIdDefault() {
	    //CoursePlan cp = new CoursePlan();

	    assertEquals(0, defaultCp.getIdCoursePlan());
	}

}

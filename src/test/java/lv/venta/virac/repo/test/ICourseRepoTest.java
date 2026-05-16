package lv.venta.virac.repo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import lv.venta.virac.model.Course;
import lv.venta.virac.repo.ICourseRepo;

@DataJpaTest
@ActiveProfiles("test")
public class ICourseRepoTest {
	
	@Autowired
    private ICourseRepo courseRepo;
	
	private Course c1;
	private Course c2;
	
	@BeforeEach
	void setUp() {
		c1 = new Course("Java Programming",2,"spring","ITF");
		c2 = new Course("Python Basics",2,"spring","ITF");
	}
	
	@Test
    void testSearchCoursesFound() {

        courseRepo.save(c1);
        courseRepo.save(c2);

        ArrayList<Course> result = courseRepo.searchCourses("Java");

        assertEquals(1, result.size());
        assertEquals("Java Programming",result.get(0).getName());
    }

    @Test
    void testSearchCoursesCaseInsensitive() {

        courseRepo.save(c1);

        ArrayList<Course> result = courseRepo.searchCourses("java");
        assertFalse(result.isEmpty());
    }

    @Test
    void testSearchCoursesPartialMatch() {

        c1.setName("Advanced Java Programming");
        courseRepo.save(c1);

        ArrayList<Course> result = courseRepo.searchCourses("Java");
        assertEquals(1, result.size());
    }

    @Test
    void testSearchCoursesEmpty() {

        courseRepo.save(c1);

        ArrayList<Course> result = courseRepo.searchCourses("Physics");
        assertTrue(result.isEmpty());
    }
}

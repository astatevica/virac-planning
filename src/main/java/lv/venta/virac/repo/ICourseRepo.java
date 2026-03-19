package lv.venta.virac.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import lv.venta.virac.model.Course;

public interface ICourseRepo extends CrudRepository<Course, Integer>{
	
	@Query(value = """
			SELECT *
			FROM course_table
			WHERE LOWER(name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			""", nativeQuery = true)
			ArrayList<Course> searchCourses(@Param("keyword")String keyword);

}

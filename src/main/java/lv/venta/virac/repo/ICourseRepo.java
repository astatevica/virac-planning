package lv.venta.virac.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import lv.venta.virac.model.Course;

public interface ICourseRepo extends CrudRepository<Course, Integer>{
	
	// Case-insensitive search
	//public abstract ArrayList<Course> findByNameContainingIgnoreCase(String keyword);
	
	// Case-sensitive search
	//public abstract ArrayList<Course> findByNameContaining(String keyword);
	
	/*@Query("""
			SELECT c 
			FROM Course c 
			WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			AND c.deleted = false
			""")
			ArrayList<Course> searchByKeyword(@Param("keyword") String keyword);*/
	
	/*@Query(value = """
			SELECT *
			FROM course_table
			WHERE LOWER(name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			""", nativeQuery = true)
			ArrayList<Course> searchNative(@Param("keyword") String keyword);*/
	
	@Query(value = """
			SELECT *
			FROM course_table
			WHERE LOWER(name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			""", nativeQuery = true)
			ArrayList<Course> searchCourses(@Param("keyword")String keyword);

}

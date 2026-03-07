package lv.venta.virac.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import lv.venta.virac.model.Course;

public interface ICourseRepo extends CrudRepository<Course, Integer>{
	
	// Case-insensitive search
	//public abstract ArrayList<Course> findByNameContainingIgnoreCase(String keyword);
	
	@Query("""
			SELECT c 
			FROM Course c 
			WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			""")
			ArrayList<Course> searchByName(@Param("keyword") String keyword);

}

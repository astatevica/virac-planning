package lv.venta.virac.repo;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import lv.venta.virac.model.Project;

public interface IProjectRepo extends CrudRepository<Project, Integer>{

	public abstract ArrayList<Project> findByNumber(int number);
	
	public abstract ArrayList<Project> findByStartDate(LocalDate startDate);
	
	public abstract ArrayList<Project> findByEndDate(LocalDate endDate);
	
	//Search project autocomplete
	@Query(value = """
			SELECT *
			FROM project_table
			WHERE LOWER(name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			""", nativeQuery = true)
			ArrayList<Project> searchProjects(@Param("keyword")String keyword);
		
}

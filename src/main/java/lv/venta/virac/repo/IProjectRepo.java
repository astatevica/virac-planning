package lv.venta.virac.repo;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.Project;

public interface IProjectRepo extends CrudRepository<Project, Integer>{

	ArrayList<Project> findByNumber(int number);
	
	ArrayList<Project> findByStartDate(LocalDate startDate);
	
	ArrayList<Project> findByEndDate(LocalDate endDate);
}

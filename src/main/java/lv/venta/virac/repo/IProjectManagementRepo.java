package lv.venta.virac.repo;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.ProjectManagement;

public interface IProjectManagementRepo extends CrudRepository<ProjectManagement, Integer>{

	public abstract ArrayList<ProjectManagement> findByEmployee_IdEmployee(int employeeId);
	
	public abstract ArrayList<ProjectManagement> findByStartDate(LocalDate startDate);
	
	public abstract ArrayList<ProjectManagement> findByEndDate(LocalDate endDate);
}

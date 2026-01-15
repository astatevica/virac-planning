package lv.venta.virac.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.Plan;

public interface IPlanRepo extends CrudRepository<Plan, Integer>{
	
	//Filter by Employee
	public abstract ArrayList<Plan> findByEmployee_IdEmployee(int employee);
	
	//Filter by Year
	public abstract ArrayList<Plan> findByYear_IdYear(int year);
	
	//Filter by Department
	public abstract ArrayList<Plan> findByEmployeeViracDepartment_IdDepartment(int year);

}

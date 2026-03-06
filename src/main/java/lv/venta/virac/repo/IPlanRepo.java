package lv.venta.virac.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import lv.venta.virac.model.Plan;

public interface IPlanRepo extends CrudRepository<Plan, Integer>{
	
	//Filter by Employee
	public abstract ArrayList<Plan> findByEmployee_IdEmployee(int employee);
	
	//Filter by Year
	public abstract ArrayList<Plan> findByYear_IdYear(int year);
	
	//Filter by Department
	public abstract ArrayList<Plan> findByEmployee_ViracDepartment_IdDepartment(int department);
	
	//Filter by Employee and Year
	public abstract ArrayList<Plan> findByEmployee_IdEmployeeAndYear_IdYear(int idEmplyee, int idYear);
	
	//Filter by Employee and Project
	@Query("""
	        SELECT DISTINCT p
	        FROM Plan p
	        JOIN p.projectPlan pp
	        WHERE p.employee.idEmployee = :employeeId
	        AND pp.project.idProject = :projectId
	        AND p.deleted = false
	        AND pp.deleted = false
	    """)
	    ArrayList<Plan> findByEmployeeAndProject(
	            @Param("employeeId") int employeeId,
	            @Param("projectId") int projectId
	    );
	
	//Filter by Employee and status
//	public abstract Plan findByEmployee_IdEmployeeAndPlanStatusIn(int idEmplyee, List<PlanStatus> statuses);
	
	//Filter by Employee and Plan
	public abstract Plan findByEmployee_IdEmployeeAndIdPlan(int idEmplyee, int idPlan);
	
	
}

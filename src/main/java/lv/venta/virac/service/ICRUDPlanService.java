package lv.venta.virac.service;

import java.util.ArrayList;

import lv.venta.virac.dto.FullPlanDTO;
import lv.venta.virac.model.Plan;

public interface ICRUDPlanService extends ICRUDBase<Plan>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE

	//C - create 
	public abstract void create(int idEmployee, int idYear, int numOfProjects, int numOfArticles, String partInConf, String partInConfEnd, 
			String comAbConf, String comAbConfEnd, int numOfCourses, int numOfStudWork, String promoOfResearch, String promoOfResearchEnd,
			String adminWork, String adminWorkEnd, String projApplicSub, String projApplicSubEnd, String skillsDevelopment,
			String skillsDevelopmentEnd, String participationInSeminars, String participationInSeminarsEnd, String otherJobs, String otherJobsEnd) throws Exception;
					
	//U - update
	public abstract void updateById(int id, int idEmployee, int idYear, int numOfProjects, int numOfArticles, String partInConf, String partInConfEnd, 
			String comAbConf, String comAbConfEnd, int numOfCourses, int numOfStudWork, String promoOfResearch, String promoOfResearchEnd,
			String adminWork, String adminWorkEnd, String projApplicSub, String projApplicSubEnd, String skillsDevelopment,
			String skillsDevelopmentEnd, String participationInSeminars, String participationInSeminarsEnd, String otherJobs, String otherJobsEnd) throws Exception;
			
	//Filter by Employee
	public abstract ArrayList<Plan> selectAllPlansByEmployee(int idEmployee) throws Exception;
	
	//Filter by Year
	public abstract ArrayList<Plan> selectAllPlansByYear(int idYear) throws Exception;
	
	//Filter by Department
	public abstract ArrayList<Plan> selectAllPlansByDepartment(String nameDepartment) throws Exception;
	
	//Filter by Employee and Year (User/Archive)
	public abstract ArrayList<Plan> selectAllPlansByEmployeeAndYear(int idEmployee, int idYear) throws Exception;
	
	//Filter by Employee and Project (User/Archive)
	public abstract ArrayList<Plan> selectAllPlansByEmployeeAndProject(int idEmployee, int idProject) throws Exception;
	
	//Get full plan for user
	//TODO: te nevajag FullPlanDTO => ArrayList<Plan>?
	public abstract FullPlanDTO getFullPlanForUser(int idEmployee, int idPlan) throws Exception;
	
	//updatePlanForUserById -> useris var editot sava plāna laukus redzot visu visu FullPlanDTO
	
	//addProjectToPlan
	//removeProjectFromPlan
	//addArticelToPlan
	//removeArticleFromPlan
	//addCourseToPlan
	//removeCourseFromPlan
	//addStudentWorkToPlan
	//removeStudentWorkFromPlan
	

}

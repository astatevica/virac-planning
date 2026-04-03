package lv.venta.virac.service;

import java.util.ArrayList;

import lv.venta.virac.model.ProjectPlan;

public interface ICRUDProjectPlanService extends ICRUDBase<ProjectPlan>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE

	//C - create 
	public abstract void create(int idPlan, int idProject, String tasks, String workDone) throws Exception;
			
	//U - update
	public abstract void updateById(int id, int idPlan, int idProject, String tasks, String workDone) throws Exception;
		
	//Filter by Plan
	public abstract ArrayList<ProjectPlan> selectAllProjectPlanByPlan(int idPlan) throws Exception;
	
	//Filter by Project
	public abstract ArrayList<ProjectPlan> selectAllProjectPlanByProject(int idProject) throws Exception;
	
	//C - create 
	public abstract void createAutocompleteProject(int idPlan, int idProject, String tasks, String workDone, int employeeId) throws Exception;

	//Delete for User current Project entity
	public abstract void deleteByProjectIdAndPlanId(int idPlan, int idProject, int employeeId) throws Exception;

	//Update for User current Project entity
	public abstract void updateByProjectIdAndPlanId(int idPlan, int idProject, String tasks, String workDone, int employeeId) throws Exception;
}

package lv.venta.virac.service;

import java.util.ArrayList;

import lv.venta.virac.dto.StudentWorkPlanResponseDTO;
import lv.venta.virac.dto.WorkPlanResponseDTO;
import lv.venta.virac.model.WorkPlan;

public interface ICRUDWorkPlanService extends ICRUDBase<WorkPlan>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE

	//C - create 
	public abstract void create(int idStudWork,int idPlan,String workDone) throws Exception;
			
	//U - update
	public abstract void updateById(int id, int idStudWork,int idPlan,String workDone) throws Exception;
		
	//Filter by Student Work
	public abstract ArrayList<WorkPlan> selectAllWorkPlanByStudentWork(int idStudWork) throws Exception;
	
	//Filter by Plan
	public abstract ArrayList<WorkPlan> selectAllWorkPlanByPlan(int idPlan) throws Exception;
	
	//Create new student-work and attach to Plan
	public abstract StudentWorkPlanResponseDTO createWorkAndAttachToPlan(int idPlan, WorkPlanResponseDTO dto, int employeeId) throws Exception;
	
	//Delete for User current Plan/Work table entity
	public abstract void deleteByWorkIdAndPlanId(int idPlan, int idStudWork, int employeeId) throws Exception;

	//Update for User current Plan table
	public abstract void updateByWorkIdAndPlanId(int idPlan, int idStudWork, String workDone, int employeeId) throws Exception;
}

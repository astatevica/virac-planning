package lv.venta.virac.service;

import java.util.ArrayList;

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
}

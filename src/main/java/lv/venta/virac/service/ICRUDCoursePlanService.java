package lv.venta.virac.service;

import java.util.ArrayList;

import lv.venta.virac.model.CoursePlan;

public interface ICRUDCoursePlanService extends ICRUDBase<CoursePlan>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE

	//C - create 
	public abstract void create(int idPlan, int idCourse, String workDone) throws Exception;
					
	//U - update
	public abstract void updateById(int id, int idPlan, int idCourse, String workDone) throws Exception;
			
	//Filter by Plan
	public abstract ArrayList<CoursePlan> selectAllCoursePlanByPlan(int idPlan) throws Exception;
	
}

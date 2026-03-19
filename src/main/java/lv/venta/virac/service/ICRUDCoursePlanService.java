package lv.venta.virac.service;

import java.util.ArrayList;

import lv.venta.virac.dto.CourseDTO;
import lv.venta.virac.dto.CoursePlanResponseDTO;
import lv.venta.virac.model.CoursePlan;

public interface ICRUDCoursePlanService extends ICRUDBase<CoursePlan>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE

	//C - create 
	public abstract void create(int idPlan, int idCourse, String workDone) throws Exception;
					
	//U - update
	public abstract void updateById(int id, int idPlan, int idCourse, String workDone) throws Exception;
			
	//Filter by Plan
	public abstract ArrayList<CoursePlan> selectAllCoursePlanByPlan(int idPlan) throws Exception; 
	
	//C - create 
	public abstract void createAutocompleteCourse(int idPlan, int idCourse, String workDone, int employeeId) throws Exception;
	
	//Create new course and attach to Plan
	public abstract CoursePlanResponseDTO createCourseAndAttachToPlan(int idPlan, CourseDTO courseDTO, String workDone, int employeeId) throws Exception;
	
	//Delete for User current Plan table
	public abstract void deleteByCourseIdAndPlanId(int idPlan, int idCourse, int employeeId) throws Exception;

	//Update for User current Plan table
	public abstract void updateByCourseIdAndPlanId(int idPlan, int idCourse, String workDone, int employeeId) throws Exception;
	
}

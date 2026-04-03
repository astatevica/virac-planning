package lv.venta.virac.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.CoursePlan;

public interface ICoursePlanRepo extends CrudRepository<CoursePlan, Integer>{
	
	public abstract ArrayList<CoursePlan> findByPlan_IdPlan(int idPlan);
	
	public abstract CoursePlan findByPlan_IdPlanAndCourse_IdCourse(int idPlan, int idCourse);
	
	//to not map deleted course-plans
	public abstract ArrayList<CoursePlan> findByPlan_IdPlanAndDeletedFalse(int idPlan);
}

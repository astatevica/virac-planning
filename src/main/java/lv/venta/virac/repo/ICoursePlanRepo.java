package lv.venta.virac.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.CoursePlan;

public interface ICoursePlanRepo extends CrudRepository<CoursePlan, Integer>{
	
	public abstract ArrayList<CoursePlan> findByPlan_IdPlan(int idPlan);

}

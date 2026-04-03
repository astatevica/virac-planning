package lv.venta.virac.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.ProjectPlan;

public interface IProjectPlanRepo extends CrudRepository<ProjectPlan, Integer>{

	public abstract ArrayList<ProjectPlan> findByPlan_IdPlan(int idPlan);
	
	public abstract ArrayList<ProjectPlan> findByProject_IdProject(int idProject);
	
	//to not map deleted work-plans
	public abstract ArrayList<ProjectPlan> findByPlan_IdPlanAndDeletedFalse(int idPlan);
	
}

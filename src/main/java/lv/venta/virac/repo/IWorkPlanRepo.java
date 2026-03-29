package lv.venta.virac.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.WorkPlan;

public interface IWorkPlanRepo extends CrudRepository<WorkPlan, Integer>{

	public abstract ArrayList<WorkPlan> findByStudentWork_IdStudWork(int idStudWork);
	
	public abstract ArrayList<WorkPlan> findByPlan_IdPlan(int idPlan);
	
	public abstract WorkPlan findByPlan_IdPlanAndStudentWork_IdStudWork(int idPlan, int idStudWork);
}

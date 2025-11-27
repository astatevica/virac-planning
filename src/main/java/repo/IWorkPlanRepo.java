package repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.WorkPlan;

public interface IWorkPlanRepo extends CrudRepository<WorkPlan, Integer>{

}

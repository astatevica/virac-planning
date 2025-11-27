package repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.ProjectPlan;

public interface IProjectPlanRepo extends CrudRepository<ProjectPlan, Integer>{

}

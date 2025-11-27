package repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.Plan;

public interface IPlanRepo extends CrudRepository<Plan, Integer>{

}

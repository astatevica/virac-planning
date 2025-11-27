package repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.CoursePlan;

public interface ICoursePlanRepo extends CrudRepository<CoursePlan, Integer>{

}

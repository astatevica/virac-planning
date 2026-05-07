package lv.venta.virac.scheduler;

import org.springframework.data.repository.CrudRepository;

public interface IPlanScheduleRepo extends CrudRepository<PlanSchedule, Integer>{
	
	//Filter by Year
	public abstract PlanSchedule findByYear_IdYear(int year);
}

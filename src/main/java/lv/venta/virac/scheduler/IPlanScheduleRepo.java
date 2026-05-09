package lv.venta.virac.scheduler;

import org.springframework.data.repository.CrudRepository;

public interface IPlanScheduleRepo extends CrudRepository<PlanSchedule, Integer>{
	
	//Filter by Year ID
	public abstract PlanSchedule findByYear_IdYear(int year);
	
	//Filter by Year Number
	public abstract PlanSchedule findByYear_YearNumber(int yearNumber);
}

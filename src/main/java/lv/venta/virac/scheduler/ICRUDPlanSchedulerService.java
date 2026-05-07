package lv.venta.virac.scheduler;

import lv.venta.virac.service.ICRUDBase;

public interface ICRUDPlanSchedulerService extends ICRUDBase<PlanSchedule>{
	
	//C - create 
	public abstract void create(SchedulerDTO dto) throws Exception;
	
	//U - update
	public abstract void update(SchedulerDTO dto) throws Exception;

}

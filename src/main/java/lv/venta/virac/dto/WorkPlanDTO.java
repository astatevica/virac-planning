package lv.venta.virac.dto;

public class WorkPlanDTO {
	
	private int idWorkPlan;
	private int idStudWork;
	private int idPlan;
	private String workDone;
	
	public WorkPlanDTO() {
		
	}
	
	public WorkPlanDTO(int idWorkPlan, int idStudWork, int idPlan, String workDone) {
		this.idWorkPlan = idWorkPlan;
		this.idStudWork = idStudWork;
		this.idPlan = idPlan;
		this.workDone = workDone;
	}
	
	public int getIdWorkPlan() {
		return idWorkPlan;
	}
	public void setIdWorkPlan(int idWorkPlan) {
		this.idWorkPlan = idWorkPlan;
	}
	public int getIdStudWork() {
		return idStudWork;
	}
	public void setIdStudWork(int idStudWork) {
		this.idStudWork = idStudWork;
	}
	public int getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(int idPlan) {
		this.idPlan = idPlan;
	}
	public String getWorkDone() {
		return workDone;
	}
	public void setWorkDone(String workDone) {
		this.workDone = workDone;
	}

}

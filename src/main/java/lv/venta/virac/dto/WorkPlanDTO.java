package lv.venta.virac.dto;

public class WorkPlanDTO {
	
	private int idWorkPlan;
	private int idStudentWork;
	private int idPlan;
	private String workDone;
	
	public WorkPlanDTO() {
		
	}
	
	public WorkPlanDTO(int idWorkPlan, int idStudentWork, int idPlan, String workDone) {
		this.idWorkPlan = idWorkPlan;
		this.idStudentWork = idStudentWork;
		this.idPlan = idPlan;
		this.workDone = workDone;
	}
	
	public int getIdWorkPlan() {
		return idWorkPlan;
	}
	public void setIdWorkPlan(int idWorkPlan) {
		this.idWorkPlan = idWorkPlan;
	}
	public int getIdStudentWork() {
		return idStudentWork;
	}
	public void setIdStudentWork(int idStudentWork) {
		this.idStudentWork = idStudentWork;
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

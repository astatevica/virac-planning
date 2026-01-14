package lv.venta.virac.dto;

public class CoursePlanDTO {
	
	private int idCoursePlan;
	private int idPlan;
	private int idCourse;
	private String workDone;
	
	public CoursePlanDTO() {
		
	}
	
	public CoursePlanDTO(int idCoursePlan, int idPlan, int idCourse, String workDone) {
		this.idCoursePlan = idCoursePlan;
		this.idPlan = idPlan;
		this.idCourse = idCourse;
		this.workDone = workDone;
	}
	
	public int getIdCoursePlan() {
		return idCoursePlan;
	}
	public void setIdCoursePlan(int idCoursePlan) {
		this.idCoursePlan = idCoursePlan;
	}
	public int getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(int idPlan) {
		this.idPlan = idPlan;
	}
	public int getIdCourse() {
		return idCourse;
	}
	public void setIdCourse(int idCourse) {
		this.idCourse = idCourse;
	}
	public String getWorkDone() {
		return workDone;
	}
	public void setWorkDone(String workDone) {
		this.workDone = workDone;
	}

}

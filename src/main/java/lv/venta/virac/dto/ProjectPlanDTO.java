package lv.venta.virac.dto;

public class ProjectPlanDTO {
	
	private int idProjectPlan;
	private int idPlan;
	private int idProject;
	private String tasks;
	private String workDone;
	
	public ProjectPlanDTO() {
		
	}
	
	public ProjectPlanDTO(int idProjectPlan, int idPlan, int idProject,String tasks,String workDone) {
		this.idProjectPlan = idProjectPlan;
		this.setIdPlan(idPlan);
		this.setIdProject(idProject);
		this.setTasks(tasks);
		this.setWorkDone(workDone);
	}

	public int getIdProjectPlan() {
		return idProjectPlan;
	}

	public int getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(int idPlan) {
		this.idPlan = idPlan;
	}

	public int getIdProject() {
		return idProject;
	}

	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}

	public String getTasks() {
		return tasks;
	}

	public void setTasks(String tasks) {
		this.tasks = tasks;
	}

	public String getWorkDone() {
		return workDone;
	}

	public void setWorkDone(String workDone) {
		this.workDone = workDone;
	}

	

}

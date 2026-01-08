package lv.venta.virac.dto;

import java.time.LocalDate;

public class ProjectDTO {
	
	private int idProject;
	private String name;
	private int number;
	private int managementId;
	private LocalDate startDate;
	private LocalDate endDate;
	private String acronym;
	
	public ProjectDTO() {
		
	}
	
	public ProjectDTO(int idProject, String name, int number, int managementId, LocalDate startDate, LocalDate endDate, String acronym) {
		this.setIdProject(idProject);
		this.setName(name);
		this.setNumber(number);
		this.setManagementId(managementId);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setAcronym(acronym);
	}

	public int getIdProject() {
		return idProject;
	}

	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getManagementId() {
		return managementId;
	}

	public void setManagementId(int managementId) {
		this.managementId = managementId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
}

package lv.venta.virac.dto;

import java.time.LocalDate;

public class ProjectManagementDTO {

	private int idProjectManag;
	private int employeeId;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public ProjectManagementDTO() {
		
	}
	
	public ProjectManagementDTO(int idProjectManag, int employeeId, LocalDate startDate, LocalDate endDate) {
		this.setIdProjectManag(idProjectManag);
		this.setEmployeeId(employeeId);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}

	public int getIdProjectManag() {
		return idProjectManag;
	}

	public void setIdProjectManag(int idProjectManag) {
		this.idProjectManag = idProjectManag;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
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
	
}

package lv.venta.virac.dto;

import java.time.LocalDate;

import lv.venta.virac.model.Employee;

public class ProjectManagementDTO {

	private int idProjectManag;
	private Employee employee;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public ProjectManagementDTO() {
		
	}
	
	public ProjectManagementDTO(int idProjectManag, Employee employee, LocalDate startDate, LocalDate endDate) {
		this.setIdProjectManag(idProjectManag); //Vai šeit ir paredzēts id setot?
		this.setEmployee(employee);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}

	public int getIdProjectManag() {
		return idProjectManag;
	}

	public void setIdProjectManag(int idProjectManag) {
		this.idProjectManag = idProjectManag;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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

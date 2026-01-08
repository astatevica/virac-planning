package lv.venta.virac.service;

import java.time.LocalDate;
import java.util.ArrayList;

import lv.venta.virac.model.Project;
import lv.venta.virac.model.ProjectManagement;

public interface ICRUDProjectService extends ICRUDBase<Project>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE

	//C - create 
	public abstract void create(String name, int number, int managementId, LocalDate startDate, LocalDate endDate, String acronym) throws Exception;
				
	//U - update
	public abstract void updateById(int id, String name, int number, int managementId, LocalDate startDate, LocalDate endDate, String acronym) throws Exception;
			
	//Filter by Employee
	public abstract ArrayList<Project> selectAllProjectsByNumber(int employeeId) throws Exception;
		
	//Filter by Start Date
	public abstract ArrayList<ProjectManagement> selectAllProjectsByStartDate(LocalDate startDate) throws Exception;

	//Filter by End Date
	public abstract ArrayList<ProjectManagement> selectAllProjectsByEndDate(LocalDate endDate) throws Exception;

	//TODO: varbūt vajag vēl kādu filtrāciju?
}

package lv.venta.virac.service;

import java.time.LocalDate;
import java.util.ArrayList;

import lv.venta.virac.model.ProjectManagement;

public interface ICRUDProjManagService extends ICRUDBase<ProjectManagement>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE

	//C - create 
	public abstract void create(int employeeId, LocalDate startDate, LocalDate endDate) throws Exception;
			
	//U - update
	public abstract void updateById(int id, int employeeId, LocalDate startDate, LocalDate endDate) throws Exception;
		
	//Filter by Employee
	public abstract ArrayList<ProjectManagement> selectAllProjectManagemetByEmployee(int employeeId) throws Exception;
	
	//Filter by Start Date
	public abstract ArrayList<ProjectManagement> selectAllProjectManagemetByStartDate(LocalDate startDate) throws Exception;

	//Filter by End Date
	public abstract ArrayList<ProjectManagement> selectAllProjectManagemetByEndDate(LocalDate endDate) throws Exception;
}

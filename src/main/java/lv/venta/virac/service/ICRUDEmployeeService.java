package lv.venta.virac.service;

import java.util.ArrayList;

import lv.venta.virac.model.Employee;
import lv.venta.virac.model.ViracDepartment;

public interface ICRUDEmployeeService extends ICRUDBase<Employee>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE

	//C - create 
	public abstract void create(String name, String surname,
			ViracDepartment department, String position) throws Exception;
		
	//U - update
	public abstract void updateById(int id, String name, String surname,
			ViracDepartment department, String position) throws Exception;
	
	//Filter by Department
	public abstract ArrayList<Employee> selectAllEmployeesByDepartment(int departmentId) throws Exception;

}

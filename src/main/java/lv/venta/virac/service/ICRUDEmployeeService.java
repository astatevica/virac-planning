package lv.venta.virac.service;

import java.util.ArrayList;

import lv.venta.virac.model.Employee;

public interface ICRUDEmployeeService extends ICRUDBase<Employee>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE

	//C - create 
	public abstract void create(String name, String surname,
			String nameDepartment, String position) throws Exception;
		
	//U - update
	public abstract void updateById(int id, String name, String surname,
			String nameDepartment, String position) throws Exception;
	
	//Filter by Department
	//TODO: iespējams jārpārtais uz nameDepartment
	public abstract ArrayList<Employee> selectAllEmployeesByDepartment(int departmentId) throws Exception;

}

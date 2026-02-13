package lv.venta.virac.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.Employee;

public interface IEmployeeRepo extends CrudRepository<Employee, Integer>{

	public abstract ArrayList<Employee> findByViracDepartment_IdDepartment(int departmentId);
	
}

package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.virac.model.Employee;
import lv.venta.virac.model.ViracDepartment;
import lv.venta.virac.repo.IEmployeeRepo;
import lv.venta.virac.service.ICRUDEmployeeService;

@Service
public class CRUDEmployeeServiceImpl implements ICRUDEmployeeService{
	
	@Autowired
	private IEmployeeRepo emplRepo;
	
	@Override
    public ArrayList<Employee> retrieveAll() throws Exception {
       ArrayList<Employee> employees = (ArrayList<Employee>) emplRepo.findAll();
       if (employees.isEmpty()) throw new Exception("There is no employee");

        return employees;
    }

    @Override
    public Employee retrieveById(int id) throws Exception {
        if (id < 1) throw new Exception("Invalid ID");
        Employee foundEmployees = emplRepo.findById(id).get();
        if (foundEmployees == null) throw new Exception("Employee with the id: (" + id + ") does not exist!");
        
        return foundEmployees;
        
    }

    @Override
    public void create(String name, String surname,
			ViracDepartment viracDepartment, String position) throws Exception {
        ArrayList<Employee> employees = (ArrayList<Employee>) emplRepo.findAll();
        
        if(name == null || surname == null || viracDepartment == null || position == null){
			throw new Exception("The input parameters are incorrect");
		}
        
        for (Employee emp : employees) {
            if (emp.getName().equals(name) & emp.getSurname().equals(surname)) {
                throw new Exception("Employee: " + emp.getName()+ emp.getSurname() + " already exists");
            }
        }

        Employee employee = new Employee(name, surname, viracDepartment, position);
        emplRepo.save(employee);
    }

    @Override
    public void updateById(int id, String name, String surname,
			ViracDepartment viracDepartment, String position) throws Exception {
    	Employee employee = retrieveById(id);
    	if (employee == null) throw new 
    		Exception("Employee with (id:" + id + ") does not exist");
    	
        employee.setName(name);
        employee.setSurname(surname);
        employee.setViracDepartment(viracDepartment);
        employee.setPosition(position);
        emplRepo.save(employee);
    }

    @Override
    public void deleteById(int id) throws Exception {
    	Employee employee = emplRepo.findById(id).get();
    	if (employee == null) throw new Exception("Employee with id:"+ id +" does not exist");
        emplRepo.delete(employee);
    }
    
    @Override
	public ArrayList<Employee> selectAllEmployeesByDepartment(ViracDepartment department) throws Exception {
		ArrayList<Employee> result = emplRepo.findByIdDepartment(department);
		if(result.isEmpty()) {
			throw new Exception("Employee with department: " + department + " does not exist");
		}
		
		return result;
	}

}

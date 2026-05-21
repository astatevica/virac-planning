package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.model.Employee;
import lv.venta.virac.model.ViracDepartment;
import lv.venta.virac.repo.IEmployeeRepo;
import lv.venta.virac.repo.IViracDepartmentRepo;
import lv.venta.virac.service.ICRUDEmployeeService;

@Service
public class CRUDEmployeeServiceImpl implements ICRUDEmployeeService{
	
	private IEmployeeRepo emplRepo;
	
	private IViracDepartmentRepo depRepo;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public CRUDEmployeeServiceImpl(IEmployeeRepo emplRepo, IViracDepartmentRepo depRepo) {
		this.emplRepo = emplRepo;
		this.depRepo = depRepo;
	}
	
	@Override
    public ArrayList<Employee> retrieveAll() throws Exception {
        
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedEmployeeFilter");
        filter.setParameter("isDeleted", false);
        ArrayList<Employee> employees = (ArrayList<Employee>) emplRepo.findAll();
        if (employees.isEmpty()) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "There is no employee"
        );
        session.disableFilter("deletedEmployeeFilter");
        return employees;
    }

    @Override
    public Employee retrieveById(int id) throws Exception {
        if (id < 1)  throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid ID"
        );
        return emplRepo.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Employee with the id: (" + id + ") does not exist!"
        ));
    }

    @Override
    public void create(String name, String surname,
			  String nameDepartment, String position) throws Exception {
        ArrayList<Employee> employees = (ArrayList<Employee>) emplRepo.findAll();
        
        if(name == null || surname == null || nameDepartment == null || position == null){
			throw new ResponseStatusException(
	                HttpStatus.BAD_REQUEST,
	                "The input parameters are incorrect"
	        );
		}
        
        ViracDepartment dep = depRepo.findByName(nameDepartment);
        if(dep == null) {
        	throw new ResponseStatusException(
	                HttpStatus.NOT_FOUND,
	                "Department not found"
	        );
        }
        
        for (Employee emp : employees) {
            if (emp.getName().equals(name) && emp.getSurname().equals(surname) && emp.isDeleted( )) {
                throw new ResponseStatusException(
    	                HttpStatus.BAD_REQUEST,
    	                "Employee: " + emp.getName()+ emp.getSurname() + " already exists"
    	        );
            }
        }

        Employee employee = new Employee(name, surname, dep, position);
        emplRepo.save(employee);
    }

    @Override
    public void updateById(int id, String name, String surname,
			String nameDepartment, String position) throws Exception {
    	Employee employee = retrieveById(id);
    	if (employee == null) throw new ResponseStatusException(
	                HttpStatus.BAD_REQUEST,
	                "Employee with (id:" + id + ") does not exist"
	        );  	
    	
    	ViracDepartment dep = depRepo.findByName(nameDepartment);
    	
        employee.setName(name);
        employee.setSurname(surname);
        employee.setViracDepartment(dep);
        employee.setPosition(position);
        emplRepo.save(employee);
    }


    @Override
    public void deleteById(int id) throws Exception {
    	Employee employee = emplRepo.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Employee with id:"+ id +" does not exist"
        ));
    	employee.setDeleted(true); // SOFT DELETE
    	emplRepo.save(employee);  // SAVE, NOT DELETE
    }
    
    @Override
	public ArrayList<Employee> selectAllEmployeesByDepartment(String nameDepartment) throws Exception {
		ArrayList<Employee> result = emplRepo.findByViracDepartment_IdDepartment(depRepo.findByName(nameDepartment).getIdDepartment());
		if(result.isEmpty()) {
			throw new ResponseStatusException(
	                HttpStatus.NOT_FOUND,
	                "Employees with department name: " + nameDepartment + " does not exist"
	        );  	
		}
		
		return result;
	}

}

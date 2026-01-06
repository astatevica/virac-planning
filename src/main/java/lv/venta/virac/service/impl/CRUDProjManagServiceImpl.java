package lv.venta.virac.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.virac.model.Employee;
import lv.venta.virac.model.ProjectManagement;
import lv.venta.virac.model.ViracDepartment;
import lv.venta.virac.repo.IEmployeeRepo;
import lv.venta.virac.repo.IProjectManagementRepo;
import lv.venta.virac.service.ICRUDProjManagService;

@Service
public class CRUDProjManagServiceImpl implements ICRUDProjManagService{

	@Autowired
	private IProjectManagementRepo managRepo;
	
	@Autowired
	private IEmployeeRepo emplRepo;
	
	@Override
	public ArrayList<ProjectManagement> retrieveAll() throws Exception {
		ArrayList<ProjectManagement> projectManag = (ArrayList<ProjectManagement>) managRepo.findAll();
	       if (projectManag.isEmpty()) throw new Exception("There is no project management");

	        return projectManag;
	}

	@Override
	public ProjectManagement retrieveById(int id) throws Exception {
		if (id < 1) throw new Exception("Invalid ID");
        ProjectManagement foundManagement = managRepo.findById(id).get();
        if (foundManagement == null) throw new Exception("Management with the id: (" + id + ") does not exist!");
        
        return foundManagement;
	}

	@Override
	public void deleteById(int id) throws Exception {
		ProjectManagement management = managRepo.findById(id).get();
    	if (management == null) throw new Exception("Management with id:"+ id +" does not exist");
        managRepo.delete(management);
		
	}

	//Jāpārskata precizitāte
	@Override
	public void create(Employee employee, LocalDate startDate, LocalDate endDate) throws Exception {
		ArrayList<ProjectManagement> managements = (ArrayList<ProjectManagement>) managRepo.findAll();
        
        if(employee == null || startDate == null || endDate == null){
			throw new Exception("The input parameters are incorrect");
		}
        
        Employee foundEmployee = emplRepo.findById(employee.getIdEmployee())
                .orElseThrow(() -> new Exception("Employee not found"));
        
        for (ProjectManagement manag : managements) {
            if (manag.getEmployee().equals(foundEmployee) & manag.getStartDate().equals(startDate)
            		& manag.getEndDate().equals(endDate) ) {
                throw new Exception("Management: " + manag.getEmployee().getIdEmployee()+ " with start date: " + manag.getStartDate() + 
                		" and end date : " + manag.getEndDate() + " already exists");
            }
        }

        ProjectManagement management = new ProjectManagement(foundEmployee, startDate, endDate);
        managRepo.save(management);
		
	}

	@Override
	public void updateById(int id, Employee employee, LocalDate startDate, LocalDate endDate) throws Exception {
		ProjectManagement management = retrieveById(id);
    	if (management == null) throw new 
    		Exception("Management with (id:" + id + ") does not exist");    	
    	
    	Employee emp = emplRepo.findById(employee.getIdEmployee())
                .orElseThrow(() -> new Exception("Employee not found"));
    	
        management.setEmployee(employee);
        management.setStartDate(startDate);
        management.setEndDate(endDate);
        managRepo.save(management);
		
	}

	@Override
	public ArrayList<ProjectManagement> selectAllProjectManagemetByEmployee(int employeeId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ProjectManagement> selectAllProjectManagemetByStartDate(LocalDate startDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ProjectManagement> selectAllProjectManagemetByEndDate(LocalDate endDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

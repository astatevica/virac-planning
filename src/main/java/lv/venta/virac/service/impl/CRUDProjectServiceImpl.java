package lv.venta.virac.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.virac.model.Employee;
import lv.venta.virac.model.Project;
import lv.venta.virac.model.ProjectManagement;
import lv.venta.virac.repo.IProjectManagementRepo;
import lv.venta.virac.repo.IProjectRepo;
import lv.venta.virac.service.ICRUDProjectService;

@Service
public class CRUDProjectServiceImpl implements ICRUDProjectService{

	@Autowired
	private IProjectRepo projRepo;
	
	@Autowired
	private IProjectManagementRepo managRepo;
	
	@Override
	public ArrayList<Project> retrieveAll() throws Exception {
		ArrayList<Project> projects = (ArrayList<Project>) projRepo.findAll();
	       if (projects.isEmpty()) throw new Exception("There is no projects");

	        return projects;
	}

	@Override
	public Project retrieveById(int id) throws Exception {
		if (id < 1) throw new Exception("Invalid ID");
        Project foundProjects = projRepo.findById(id).get();
        if (foundProjects == null) throw new Exception("Project with the id: (" + id + ") does not exist!");
        
        return foundProjects;
	}

	@Override
	public void deleteById(int id) throws Exception {
		Project projects = projRepo.findById(id).get();
    	if (projects == null) throw new Exception("Project with id:"+ id +" does not exist");
        projRepo.delete(projects);
		
	}

	@Override
	public void create(String name, int number, int managementId, LocalDate startDate, LocalDate endDate,
			String acronym) throws Exception {
		ArrayList<Project> projects = (ArrayList<Project>) projRepo.findAll();
        
        if(name == null || number == 0  || startDate == null || endDate == null){
			throw new Exception("The input parameters are incorrect");
		}
        
        Employee foundEmployee = emplRepo.findById(employeeId)
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
	public void updateById(int id, String name, int number, int managementId, LocalDate startDate, LocalDate endDate,
			String acronym) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Project> selectAllProjectsByNumber(int employeeId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ProjectManagement> selectAllProjectsByStartDate(LocalDate startDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ProjectManagement> selectAllProjectsByEndDate(LocalDate endDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}

package lv.venta.virac.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        
        if(name == null || number == 0  || startDate == null || endDate == null || acronym == null){
			throw new Exception("The input parameters are incorrect");
		}
        
        ProjectManagement foundManagement = managRepo.findById(managementId)
                .orElseThrow(() -> new Exception("Management not found"));
        
        for (Project proj : projects) {
            if (proj.getName().equals(name)& proj.getNumber() == number & proj.getProjectManagement().getIdProjectManag() == managementId & 
            		proj.getStartDate().equals(startDate)& proj.getEndDate().equals(endDate) & proj.getAcronym().equals(acronym) ) {
                throw new Exception("Project: " + proj.getName()+ " with number: " + proj.getNumber() + " already exists");
            }
        }

        Project project = new Project(name, number, foundManagement, startDate, endDate, acronym);
        projRepo.save(project);
	}

	@Override
	public void updateById(int id, String name, int number, int managementId, LocalDate startDate, LocalDate endDate,
			String acronym) throws Exception {
		Project project = retrieveById(id);
    	if (project == null) throw new 
    		Exception("Project with (id:" + id + ") does not exist");    	
    	
    	ProjectManagement manag = managRepo.findById(managementId)
                .orElseThrow(() -> new Exception("Management not found"));
    	
        project.setName(name);
        project.setNumber(number);
        project.setProjectManagement(manag);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setAcronym(acronym);
        projRepo.save(project);
		
	}

	@Override
	public ArrayList<Project> selectAllProjectsByNumber(int number) throws Exception {
		ArrayList<Project> result = projRepo.findByNumber(number);
		
		if(result.isEmpty()) {
			throw new Exception("Project with number: " + number + " does not exist");
		}
		
		return result;
	}

	@Override
	public ArrayList<Project> selectAllProjectsByStartDate(LocalDate startDate) throws Exception {
		ArrayList<Project> result = projRepo.findByStartDate(startDate);
		
		if(result.isEmpty()) {
			throw new Exception("Start date: " + startDate + " does not exist");
		}
		
		return result;
	}

	@Override
	public ArrayList<Project> selectAllProjectsByEndDate(LocalDate endDate) throws Exception {
		ArrayList<Project> result = projRepo.findByEndDate(endDate);
		
		if(result.isEmpty()) {
			throw new Exception("End date: " + endDate + " does not exist");
		}
		
		return result;
	}

	
	

}

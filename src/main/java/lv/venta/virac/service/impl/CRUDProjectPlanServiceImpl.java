package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.model.Plan;
import lv.venta.virac.model.Project;
import lv.venta.virac.model.ProjectPlan;
import lv.venta.virac.repo.IPlanRepo;
import lv.venta.virac.repo.IProjectPlanRepo;
import lv.venta.virac.repo.IProjectRepo;
import lv.venta.virac.service.ICRUDProjectPlanService;

@Service
public class CRUDProjectPlanServiceImpl implements ICRUDProjectPlanService{

	@Autowired
	private IProjectPlanRepo projPlanRepo;
	
	@Autowired
	private IProjectRepo projRepo;
	
	@Autowired
	private IPlanRepo planRepo;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public ArrayList<ProjectPlan> retrieveAll() throws Exception {
		Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedProjectPlanFilter");
        filter.setParameter("isDeleted", false);
        ArrayList<ProjectPlan> projectPlan = (ArrayList<ProjectPlan>) projPlanRepo.findAll();
        if (projectPlan.isEmpty()) throw new Exception("There is no Project Plan");
        session.disableFilter("deletedProjectPlanFilter");
        return projectPlan;
	}

	@Override
	public ProjectPlan retrieveById(int id) throws Exception {
		if (id < 1) throw new Exception("Invalid ID");
        ProjectPlan foundProjectPlan = projPlanRepo.findById(id).get();
        if (foundProjectPlan == null) throw new Exception("ProjectPlan with the id: (" + id + ") does not exist!");
        
        return foundProjectPlan;
	}

	@Override
	public void deleteById(int id) throws Exception {
		ProjectPlan projectPlan = projPlanRepo.findById(id).get();
    	if (projectPlan == null) throw new Exception("Project Plan with id:"+ id +" does not exist");
    	projectPlan.setDeleted(true); // SOFT DELETE
    	projPlanRepo.save(projectPlan);  // SAVE, NOT DELETE
	}

	@Override
	public void create(int idPlan, int idProject, String tasks, String workDone) throws Exception {
		ArrayList<ProjectPlan> projectPlans = (ArrayList<ProjectPlan>) projPlanRepo.findAll();
        
        if(idPlan == 0 || idProject == 0 || tasks == null){
			throw new Exception("The input parameters are incorrect");
		}
        
        Plan plan = planRepo.findById(idPlan).get();
        if(plan == null) {
        	throw new Exception("Plan not found");
        }
        
        Project project = projRepo.findById(idProject).get();
        if(project == null) {
        	throw new Exception("Project not found");
        }
        
        for (ProjectPlan projPlan : projectPlans) {
            if (projPlan.getPlan().getIdPlan() == idPlan && projPlan.getProject().getIdProject() == idProject &&
            		projPlan.getTasks().equals(tasks) && projPlan.getWorkDone().equals(workDone) && projPlan.isDeleted( )== false) {
                throw new Exception("Project-Plan with PLAN_ID: " + projPlan.getPlan().getIdPlan() + " and PROJECT_ID: " 
            		+ projPlan.getProject().getIdProject() + " already exists");
            }
        }

        ProjectPlan projectPlan = new ProjectPlan(plan, project, tasks, workDone);
        projPlanRepo.save(projectPlan);
	}

	@Override
	public void updateById(int id, int idPlan, int idProject, String tasks, String workDone) throws Exception {
		ProjectPlan projectPlan = retrieveById(id);
    	if (projectPlan == null) throw new 
    		Exception("Project-Plan with (id:" + id + ") does not exist");    	
    	
    	Plan plan = planRepo.findById(idPlan).get();
        if(plan == null) {
        	throw new Exception("Plan not found");
        }
        
        Project project = projRepo.findById(idProject).get();
        if(project == null) {
        	throw new Exception("Project not found");
        }
    	
        projectPlan.setPlan(plan);
        projectPlan.setProject(project);
        projectPlan.setTasks(tasks);
        projectPlan.setWorkDone(workDone);
        projPlanRepo.save(projectPlan);
		
	}

	@Override
	public ArrayList<ProjectPlan> selectAllProjectPlanByPlan(int idPlan) throws Exception {
		ArrayList<ProjectPlan> result = projPlanRepo.findByPlan_IdPlan(idPlan);
		if(result.isEmpty()) {
			result = null;
			//throw new Exception("Project-Plan with PLAN ID: " + idPlan + " does not exist");
		}
		
		return result;
	}

	@Override
	public ArrayList<ProjectPlan> selectAllProjectPlanByProject(int idProject) throws Exception {
		ArrayList<ProjectPlan> result = projPlanRepo.findByProject_IdProject(idProject);
		if(result.isEmpty()) {
			throw new Exception("Project-Plan with PROJECT ID: " + idProject + " does not exist");
		}
		
		return result;
	}

}

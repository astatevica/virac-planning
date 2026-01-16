package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.model.Plan;
import lv.venta.virac.model.StudentWork;
import lv.venta.virac.model.WorkPlan;
import lv.venta.virac.repo.IPlanRepo;
import lv.venta.virac.repo.IStudentWorkRepo;
import lv.venta.virac.repo.IWorkPlanRepo;
import lv.venta.virac.service.ICRUDWorkPlanService;

@Service
public class CRUDWorkPlanServiceImpl implements ICRUDWorkPlanService{

	@Autowired
	private IWorkPlanRepo workPlanRepo;
	
	@Autowired
	private IStudentWorkRepo studWorkRepo;
	
	@Autowired
	private IPlanRepo planRepo;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public ArrayList<WorkPlan> retrieveAll() throws Exception {
		Session session = entityManager.unwrap(Session.class); 
        Filter filter = session.enableFilter("deletedWorkPlanFilter");
        filter.setParameter("isDeleted", false);
        ArrayList<WorkPlan> workPlans = (ArrayList<WorkPlan>) workPlanRepo.findAll();
        if (workPlans.isEmpty()) throw new Exception("There is no Work-Plans");
        session.disableFilter("deletedWorkPlanFilter");
        return workPlans;
	}

	@Override
	public WorkPlan retrieveById(int id) throws Exception {
		if (id < 1) throw new Exception("Invalid ID");
        WorkPlan foundWorkPlans = workPlanRepo.findById(id).get();
        if (foundWorkPlans == null) throw new Exception("Work-Plan with the id: (" + id + ") does not exist!");
        
        return foundWorkPlans;
	}

	@Override
	public void deleteById(int id) throws Exception {
		WorkPlan workPlans = workPlanRepo.findById(id).get();
    	if (workPlans == null) throw new Exception("Work-Plan with id:"+ id +" does not exist");
    	workPlans.setDeleted(true); // SOFT DELETE
    	workPlanRepo.save(workPlans);  // SAVE, NOT DELETE
	}

	@Override
	public void create(int idStudWork, int idPlan, String workDone) throws Exception {
		ArrayList<WorkPlan> workPlans = (ArrayList<WorkPlan>) workPlanRepo.findAll();
		System.out.println(workPlans);
		System.out.println("idStudWork: " + idStudWork + " idPlan: " + idPlan + " workDone: " + workDone );
		
        if(idStudWork == 0 || idPlan == 0){
			throw new Exception("The input parameters are incorrect");
		}
        
        StudentWork sw = studWorkRepo.findById(idStudWork).get();
        if(sw == null) {
        	throw new Exception("Student-Work not found");
        }
        
        Plan pl = planRepo.findById(idPlan).get();
        if(pl == null) {
        	throw new Exception("Plan not found");
        }
        
        for (WorkPlan wp : workPlans) {
            if (wp.getStudentWork().getIdStudWork()==idStudWork & wp.getPlan().getIdPlan()==0 & wp.isDeleted( )== false) {
                throw new Exception("Work-Plan with ID: " + wp.getIdWorkPlan() + " already exists");
            }
        }

        WorkPlan wp = new WorkPlan(sw,pl,workDone);
        workPlanRepo.save(wp);
		
	}

	@Override
	public void updateById(int id, int idStudWork, int idPlan, String workDone) throws Exception {
		WorkPlan wp = retrieveById(id);
    	if (wp == null) throw new 
    		Exception("Work-Plan with (id:" + id + ") does not exist");    	
    	
    	StudentWork sw = studWorkRepo.findById(idStudWork).get();
        if(sw == null) {
        	throw new Exception("Student-Work not found");
        }
        
        Plan pl = planRepo.findById(idPlan).get();
        if(pl == null) {
        	throw new Exception("Plan not found");
        }
    	
        wp.setStudentWork(sw);
        wp.setPlan(pl);
        wp.setWorkDone(workDone);
        workPlanRepo.save(wp);
	}

	@Override
	public ArrayList<WorkPlan> selectAllWorkPlanByStudentWork(int idStudWork) throws Exception {
		ArrayList<WorkPlan> result = workPlanRepo.findByStudentWork_IdStudWork(idStudWork);
		if(result.isEmpty()) {
			throw new Exception("Student-Work with Student-Work ID: " + idStudWork + " does not exist");
		}
		
		return result;
	}

	@Override
	public ArrayList<WorkPlan> selectAllWorkPlanByPlan(int idPlan) throws Exception {
		ArrayList<WorkPlan> result = workPlanRepo.findByPlan_IdPlan(idPlan);
		if(result.isEmpty()) {
			throw new Exception("Student-Work with Plan ID: " + idPlan + " does not exist");
		}
		
		return result;
	}

}

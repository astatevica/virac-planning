package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.dto.StudentWorkPlanResponseDTO;
import lv.venta.virac.dto.WorkPlanResponseDTO;
import lv.venta.virac.model.Plan;
import lv.venta.virac.model.StudentWork;
import lv.venta.virac.model.WorkPlan;
import lv.venta.virac.model.enums.Degree;
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

	@Override
	public StudentWorkPlanResponseDTO createWorkAndAttachToPlan(int idPlan, WorkPlanResponseDTO dto, int employeeId)
			throws Exception {
		//variables for easier use
		String name = dto.getName();
		String studentName = dto.getStudentName();
		String studentSurname = dto.getStudentSurname();
		String degree = dto.getDegree().name();
		String workDone = dto.getWorkDone();
		
		//reads already made student work
		ArrayList<StudentWork> studentWork = (ArrayList<StudentWork>) studWorkRepo.findAll();
        
		//verifies that student work and idPlan input parameters are not empty
        if(name == null || studentName == null || studentSurname == null || degree == null || idPlan == 0){
			throw new Exception("The input parameters are incorrect");
		}
        
        //verifies that course already is not made
        for (StudentWork sw : studentWork) {
            if (sw.getName().equals(name) & sw.getStudentName().equals(studentName) & sw.getStudentSurname().equals(studentSurname) & 
            		sw.getDegree().toString().equals(degree) & sw.isDeleted( )== false) {
                throw new Exception("Student work: " + sw.getName() + " already exists");
            }
        }
        
        //verifies that plan is correct
        Plan plan = planRepo.findById(idPlan).get();
        if(plan == null) {
        	throw new Exception("Plan not found");
        }    
        
        //verifies that plan is connected to right user
        if(employeeId != plan.getEmployee().getIdEmployee()) {
        	throw new Exception("This user: "+ plan.getEmployee().getName() + " " + plan.getEmployee().getSurname() +" can't edit current plan");
        }
	    
        //Makes new Student work after veryfing
		StudentWork w = new StudentWork();
	    w.setName(name);
	    w.setStudentName(studentName);
	    w.setStudentSurname(studentSurname);
	    w.setDegree(Degree.valueOf(degree));
	    StudentWork newStudentWork = studWorkRepo.save(w);
	    
	    //Creates new Plan-Work relation
	    WorkPlan wp = new WorkPlan();
	    wp.setPlan(planRepo.findById(idPlan).get());
	    wp.setStudentWork(newStudentWork);
	    wp.setDeleted(false);
	    wp.setWorkDone(workDone);
	    workPlanRepo.save(wp);
	    
	    //Returns WorkPlan dto for frontend
	    StudentWorkPlanResponseDTO swdto = new StudentWorkPlanResponseDTO();
	    swdto.setName(name);
	    swdto.setStudentName(studentName);
	    swdto.setStudentSurname(studentSurname);
	    swdto.setDegree(Degree.valueOf(degree));
	    swdto.setWorkDone(workDone);

	    return swdto;
	}

	@Override
	public void deleteByWorkIdAndPlanId(int idPlan, int idStudWork, int employeeId) throws Exception {
		WorkPlan workPlan = workPlanRepo.findByPlan_IdPlanAndStudentWork_IdStudWork(idPlan,idStudWork);
    	if (workPlan == null) throw new Exception("Work-Plan with Plan id:"+ idPlan +" and Student Work id: "+idStudWork+" does not exist");
    	if(employeeId != workPlan.getCreatedBy()) {
        	throw new Exception("This user: "+ workPlan.getPlan().getEmployee().getName() + " " 
        			+ workPlan.getPlan().getEmployee().getSurname() +" can't edit current plan");
        }
    	workPlan.setDeleted(true); // SOFT DELETE
    	workPlanRepo.save(workPlan);  // SAVE, NOT DELETE
		
	}

	@Override
	public void updateByWorkIdAndPlanId(int idPlan, int idStudWork, String workDone, int employeeId) throws Exception {
		WorkPlan workPlan = workPlanRepo.findByPlan_IdPlanAndStudentWork_IdStudWork(idPlan,idStudWork);
		if (workPlan == null) throw new Exception("Work-Plan with Plan id:"+ idPlan +" and Student Work id: "+idStudWork+" does not exist");
    	if(employeeId != workPlan.getCreatedBy()) {
        	throw new Exception("This user: "+ workPlan.getPlan().getEmployee().getName() + " " 
        			+ workPlan.getPlan().getEmployee().getSurname() +" can't edit current plan");
        }
    	workPlan.setWorkDone(workDone);
    	workPlanRepo.save(workPlan);
		
	}

}

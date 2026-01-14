package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.model.Course;
import lv.venta.virac.model.CoursePlan;
import lv.venta.virac.model.Plan;
import lv.venta.virac.repo.ICoursePlanRepo;
import lv.venta.virac.repo.ICourseRepo;
import lv.venta.virac.repo.IPlanRepo;
import lv.venta.virac.service.ICRUDCoursePlanService;

@Service
public class CRUDCoursePlanServiceImpl implements ICRUDCoursePlanService{
	
	@Autowired
	private ICoursePlanRepo coursePlanRepo;
	
	@Autowired
	private ICourseRepo courseRepo;
	
	@Autowired
	private IPlanRepo planRepo;
	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
	public ArrayList<CoursePlan> retrieveAll() throws Exception {
		Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedCoursePlanFilter");
        filter.setParameter("isDeleted", false);
        ArrayList<CoursePlan> coursePlans = (ArrayList<CoursePlan>) coursePlanRepo.findAll();
        if (coursePlans.isEmpty()) throw new Exception("There is no course-plans");
        session.disableFilter("deletedCoursePlanFilter");
        return coursePlans;
	}

	@Override
	public CoursePlan retrieveById(int id) throws Exception {
		if (id < 1) throw new Exception("Invalid ID");
        CoursePlan foundCoursePlan = coursePlanRepo.findById(id).get();
        if (foundCoursePlan == null) throw new Exception("Course-Plan with the id: (" + id + ") does not exist!");
        
        return foundCoursePlan;
	}

	@Override
	public void deleteById(int id) throws Exception {
		CoursePlan coursePlan = coursePlanRepo.findById(id).get();
    	if (coursePlan == null) throw new Exception("Course-Plan with id:"+ id +" does not exist");
    	coursePlan.setDeleted(true); // SOFT DELETE
    	coursePlanRepo.save(coursePlan);  // SAVE, NOT DELETE
	}

	@Override
	public void create(int idPlan, int idCourse, String workDone) throws Exception {
		ArrayList<CoursePlan> coursePlans = (ArrayList<CoursePlan>) coursePlanRepo.findAll();
        
        if(idPlan == 0 || idCourse == 0){
			throw new Exception("The input parameters are incorrect");
		}
        
        Plan plan = planRepo.findById(idPlan).get();
        if(plan == null) {
        	throw new Exception("Plan not found");
        }
        
        Course course = courseRepo.findById(idCourse).get();
        if(course == null) {
        	throw new Exception("Course not found");
        }
        
        for (CoursePlan cp : coursePlans) {
            if (cp.getPlan().getIdPlan() == idPlan & cp.getCourse().getIdCourse() == idCourse) {
                throw new Exception("Course-plan with paln id: " + cp.getPlan().getIdPlan() + " and course id: " 
            + cp.getCourse().getIdCourse() + " already exists");
            }
        }

        CoursePlan coursePlan = new CoursePlan(plan, course, workDone);
        coursePlanRepo.save(coursePlan);
		
	}

	@Override
	public void updateById(int id, int idPlan, int idCourse, String workDone) throws Exception {
		CoursePlan coursePlan = retrieveById(id);
    	if (coursePlan == null) throw new 
    		Exception("Course-plan with (id:" + id + ") does not exist");    	
    	
    	Plan plan = planRepo.findById(idPlan).get();
        if(plan == null) {
        	throw new Exception("Plan not found");
        }
        
        Course course = courseRepo.findById(idCourse).get();
        if(course == null) {
        	throw new Exception("Course not found");
        }
    	
        coursePlan.setCourse(course);
        coursePlan.setPlan(plan);
        coursePlan.setWorkDone(workDone);
        coursePlanRepo.save(coursePlan);
		
	}

	@Override
	public ArrayList<CoursePlan> selectAllCoursePlanByPlan(int idPlan) throws Exception {
		ArrayList<CoursePlan> result = coursePlanRepo.findByPlan_IdPlan(idPlan);
		if(result.isEmpty()) {
			throw new Exception("Course-plan with plan ID: " + idPlan + " does not exist");
		}
		
		return result;
	}

}

package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.dto.CourseDTO;
import lv.venta.virac.dto.CoursePlanResponseDTO;
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
		CoursePlan cp = coursePlanRepo.findByPlan_IdPlanAndCourse_IdCourse(idPlan,idCourse);
        
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
        
        if(cp != null) {
            if(!cp.isDeleted()){
                throw new Exception("Course already attached to this plan");
            }
            cp.setDeleted(false);
            cp.setWorkDone(workDone);
            coursePlanRepo.save(cp);
            return;
        }
        CoursePlan coursePlan = new CoursePlan(plan, course, workDone);
        coursePlanRepo.save(coursePlan);
        System.out.println("idCoursePlan: " + coursePlan.getIdCoursePlan() + " idPlan: " + idPlan + " idCourse: " + idCourse + " WorkDone: " + workDone);
		
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

	@Override
	public CoursePlanResponseDTO createCourseAndAttachToPlan(int idPlan, CourseDTO courseDTO, String workDone) throws Exception {
		//variables for easier use
		String name = courseDTO.getName();
		int ectsCredits = courseDTO.getEctsCredits();
		String semester = courseDTO.getSemester();
		String faculty = courseDTO.getFaculty();
		System.out.println("Name: " + name + " ECTS: " + ectsCredits + " Semester: " + semester + " Faculty: " + faculty);
		
		//reads already made courses
		ArrayList<Course> courses = (ArrayList<Course>) courseRepo.findAll();
        
		//verifies that course and idPlan input parameters are not empty
        if(name == null || ectsCredits == 0 || semester == null || faculty == null || idPlan == 0){
			throw new Exception("The input parameters are incorrect");
		}
        
        //verifies that course already is not made
        for (Course co : courses) {
            if (co.getName().equals(name) & co.getEctsCredits()==ectsCredits & co.getSemester().equals(semester) & co.getFaculty().equals(faculty) & co.isDeleted( )== false) {
                throw new Exception("Course: " + co.getName() + " already exists");
            }
        }
        
        //verifies that plan is correct
        Plan plan = planRepo.findById(idPlan).get();
        if(plan == null) {
        	throw new Exception("Plan not found");
        }        
	    
        //Makes new Course after veryfing
		Course c = new Course();
	    c.setName(name);
	    c.setEctsCredits(ectsCredits);
	    c.setSemester(semester);
	    c.setFaculty(faculty);
	    Course newCourse = courseRepo.save(c);
	    System.out.println("New course: " + newCourse);
	    
	    //Creates new Plan-Course relation
	    CoursePlan cp = new CoursePlan();
	    cp.setPlan(planRepo.findById(idPlan).get());
	    cp.setCourse(newCourse);
	    cp.setDeleted(false);
	    cp.setWorkDone(workDone);
	    coursePlanRepo.save(cp);
	    System.out.println("New Course-Plan: " + cp);
	    
	    //Returns CoursePlan dto for frontend
	    CoursePlanResponseDTO dto = new CoursePlanResponseDTO();
	    dto.setEctsCredits(ectsCredits);
	    dto.setFaculty(faculty);
	    dto.setName(name);
	    dto.setSemester(semester);
	    dto.setWorkDone(workDone);

	    return dto;
		
	}
	
	//TODO:Auditing
	//TODO: pieliekt workDone un sasaistīt ar DTO
	@Override
	public void deleteByCourseIdAndPlanId(int idPlan, int idCourse) throws Exception {
		CoursePlan coursePlan = coursePlanRepo.findByPlan_IdPlanAndCourse_IdCourse(idPlan,idCourse);
    	if (coursePlan == null) throw new Exception("Course-Plan with Plan id:"+ idPlan +" and Course id: "+idCourse+" does not exist");
    	coursePlan.setDeleted(true); // SOFT DELETE
    	coursePlanRepo.save(coursePlan);  // SAVE, NOT DELETE
	}
	
	@Override
	public void updateByCourseIdAndPlanId(int idPlan, int idCourse, String workDone) throws Exception {
		CoursePlan coursePlan = coursePlanRepo.findByPlan_IdPlanAndCourse_IdCourse(idPlan,idCourse);
    	if (coursePlan == null) throw new Exception("Course-Plan with Plan id:"+ idPlan +" and Course id: "+idCourse+" does not exist");
    	
        coursePlan.setWorkDone(workDone);
        coursePlanRepo.save(coursePlan);

	}

}

package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.model.Employee;
import lv.venta.virac.model.Plan;
import lv.venta.virac.model.Year;
import lv.venta.virac.repo.IEmployeeRepo;
import lv.venta.virac.repo.IPlanRepo;
import lv.venta.virac.repo.IViracDepartmentRepo;
import lv.venta.virac.repo.IYearRepo;
import lv.venta.virac.service.ICRUDPlanService;

@Service
public class CRUDPlanServiceImpl implements ICRUDPlanService{
	
	@Autowired
	private IPlanRepo planRepo;
	
	@Autowired
	private IEmployeeRepo employeeRepo;
	
	@Autowired
	private IYearRepo yearRepo;
	
	@Autowired
	private IViracDepartmentRepo depRepo;
	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
	public ArrayList<Plan> retrieveAll() throws Exception {
		Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedPlanFilter");
        filter.setParameter("isDeleted", false);
        ArrayList<Plan> plans = (ArrayList<Plan>) planRepo.findAll();
        if (plans.isEmpty()) throw new Exception("There is no plans");
        session.disableFilter("deletedPlanFilter");
        return plans;
	}

	@Override
	public Plan retrieveById(int id) throws Exception {
		if (id < 1) throw new Exception("Invalid ID");
        Plan foundPlan = planRepo.findById(id).get();
        if (foundPlan == null) throw new Exception("Plan with the id: (" + id + ") does not exist!");
        
        return foundPlan;
	}

	@Override
	public void deleteById(int id) throws Exception {
		Plan plan = planRepo.findById(id).get();
    	if (plan == null) throw new Exception("Plan with id:"+ id +" does not exist");
    	plan.setDeleted(true); // SOFT DELETE
    	planRepo.save(plan);  // SAVE, NOT DELETE
	}

	@Override
	public void create(int idEmployee, int idYear, int numOfProjects, int numOfArticles, String partInConf,
			String partInConfEnd, String comAbConf, String comAbConfEnd, int numOfCourses, int numOfStudWork,
			String promoOfResearch, String promoOfResearchEnd, String adminWork, String adminWorkEnd,
			String projApplicSub, String projApplicSubEnd, String skillsDevelopment, String skillsDevelopmentEnd,
			String participationInSeminars, String participationInSeminarsEnd, String otherJobs, String otherJobsEnd) throws Exception {
		
		ArrayList<Plan> plans = (ArrayList<Plan>) planRepo.findAll();
        
        if(idEmployee == 0 || idYear == 0){
			throw new Exception("The input parameters are incorrect");
		}
        
        Employee employee = employeeRepo.findById(idEmployee).get();
        if(employee == null) {
        	throw new Exception("Employee not found");
        }
        
        Year year = yearRepo.findById(idYear).get();
        if(year == null) {
        	throw new Exception("Year not found");
        }
        
        for (Plan pl : plans) {
            if (pl.getEmployee().getIdEmployee() == idEmployee & pl.getYear().getIdYear() == idYear & pl.isDeleted( )== false) {
                throw new Exception("Plan with paln Employee ID: " + pl.getEmployee().getIdEmployee() + " and Year ID: " 
            + pl.getYear().getIdYear() + " already exists");
            }
        }

        Plan plan = new Plan(employee, year, numOfProjects, numOfArticles, partInConf,partInConfEnd, comAbConf, comAbConfEnd, numOfCourses, 
        		numOfStudWork, promoOfResearch, promoOfResearchEnd, adminWork, adminWorkEnd, projApplicSub, projApplicSubEnd, skillsDevelopment,
        		skillsDevelopmentEnd, participationInSeminars, participationInSeminarsEnd, otherJobs, otherJobsEnd);
        planRepo.save(plan);
		
	}

	@Override
	public void updateById(int id, int idEmployee, int idYear, int numOfProjects, int numOfArticles, String partInConf,
			String partInConfEnd, String comAbConf, String comAbConfEnd, int numOfCourses, int numOfStudWork,
			String promoOfResearch, String promoOfResearchEnd, String adminWork, String adminWorkEnd,
			String projApplicSub, String projApplicSubEnd, String skillsDevelopment, String skillsDevelopmentEnd,
			String participationInSeminars, String participationInSeminarsEnd, String otherJobs, String otherJobsEnd) throws Exception {
		
		Plan plan = retrieveById(id);
    	if (plan == null) throw new 
    		Exception("Plan with (id:" + id + ") does not exist");    	
    	
    	Employee employee = employeeRepo.findById(idEmployee).get();
        if(employee == null) {
        	throw new Exception("Employee not found");
        }
        
        Year year = yearRepo.findById(idYear).get();
        if(year == null) {
        	throw new Exception("Year not found");
        }
    	
        plan.setEmployee(employee);
        plan.setYear(year);
        plan.setNumOfProjects(numOfProjects);
        plan.setNumOfArticles(numOfArticles);
        plan.setPartInConf(partInConf);
        plan.setPartInConfEnd(partInConfEnd);
        plan.setComAbConf(comAbConf);
        plan.setComAbConfEnd(comAbConfEnd);
        plan.setNumOfCourses(numOfCourses);
        plan.setNumOfStudWork(numOfStudWork);
        plan.setPromoOfResearch(promoOfResearch);
        plan.setPromoOfResearchEnd(promoOfResearchEnd);
        plan.setAdminWork(adminWork);
        plan.setAdminWorkEnd(adminWorkEnd);
        plan.setProjApplicSub(projApplicSub);
        plan.setProjApplicSubEnd(projApplicSubEnd);
        plan.setSkillsDevelopment(skillsDevelopment);
        plan.setSkillsDevelopmentEnd(skillsDevelopmentEnd);
        plan.setParticipationInSeminars(participationInSeminars);
        plan.setParticipationInSeminarsEnd(participationInSeminarsEnd);
        plan.setOtherJobs(otherJobs);
        plan.setOtherJobsEnd(otherJobsEnd);
        planRepo.save(plan);
		
	}

	@Override
	public ArrayList<Plan> selectAllPlansByEmployee(int idEmployee) throws Exception {
		ArrayList<Plan> result = planRepo.findByEmployee_IdEmployee(idEmployee);
		if(result.isEmpty()) {
			throw new Exception("Plan with Employee ID: " + idEmployee + " does not exist");
		}
		
		return result;
	}

	@Override
	public ArrayList<Plan> selectAllPlansByYear(int idYear) throws Exception {
		ArrayList<Plan> result = planRepo.findByYear_IdYear(idYear);
		if(result.isEmpty()) {
			throw new Exception("Plan with Year ID: " + idYear + " does not exist");
		}
		
		return result;
	}

	@Override
	public ArrayList<Plan> selectAllPlansByDepartment(String nameDepartment) throws Exception {
		ArrayList<Plan> result = planRepo.findByEmployeeViracDepartment_IdDepartment(depRepo.findByName(nameDepartment).getIdDepartment());
		if(result.isEmpty()) {
			throw new Exception("Plan with in Department: " + nameDepartment + " does not exist");
		}
		
		return result;
	}

}

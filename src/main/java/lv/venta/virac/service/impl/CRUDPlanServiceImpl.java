package lv.venta.virac.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.dto.CoursePlanResponseDTO;
import lv.venta.virac.dto.FullPlanDTO;
import lv.venta.virac.dto.ProjectPlanResponseDTO;
import lv.venta.virac.dto.ScientificArticlesResponseDTO;
import lv.venta.virac.dto.WorkPlanResponseDTO;
import lv.venta.virac.model.Employee;
import lv.venta.virac.model.Plan;
import lv.venta.virac.model.Year;
import lv.venta.virac.model.enums.PlanStatus;
import lv.venta.virac.repo.IArticlePlanRepo;
import lv.venta.virac.repo.ICoursePlanRepo;
import lv.venta.virac.repo.IEmployeeRepo;
import lv.venta.virac.repo.IPlanRepo;
import lv.venta.virac.repo.IProjectPlanRepo;
import lv.venta.virac.repo.IViracDepartmentRepo;
import lv.venta.virac.repo.IWorkPlanRepo;
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
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IProjectPlanRepo projectPlanRepo;

	@Autowired
	private ICoursePlanRepo coursePlanRepo;

	@Autowired
	private IArticlePlanRepo articlePlanRepo;

	@Autowired
	private IWorkPlanRepo studentWorkPlanRepo;

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
        		skillsDevelopmentEnd, participationInSeminars, participationInSeminarsEnd, otherJobs, otherJobsEnd,PlanStatus.plan_open);
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
		System.out.println(result);
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
		ArrayList<Plan> result = planRepo.findByEmployee_ViracDepartment_IdDepartment(depRepo.findByName(nameDepartment).getIdDepartment());
		if(result.isEmpty()) {
			throw new Exception("Plan with in Department: " + nameDepartment + " does not exist");
		}
		
		return result;
	}

	@Override
	public ArrayList<Plan> selectAllPlansByEmployeeAndYear(int idEmployee, int idYear) throws Exception {
		ArrayList<Plan> result = planRepo.findByEmployee_IdEmployeeAndYear_IdYear(idEmployee, idYear);
		if(result.isEmpty()) {
			throw new Exception("Plan with ID Emplyee: " + idEmployee + " and ID Year: "+ idYear + " does not exist");
		}
		System.out.println(result);
		return result;
	}

	@Override
	public ArrayList<Plan> selectAllPlansByEmployeeAndProject(int idEmployee, int idProject) throws Exception {
		ArrayList<Plan> result = planRepo.findByEmployeeAndProject(idEmployee, idProject);
		if(result.isEmpty()) {
			throw new Exception("Plan with ID Emplyee: " + idEmployee + " and ID Project: "+ idProject + " does not exist");
		}
		System.out.println(result);
		return result;
	}

	@Override
	public FullPlanDTO getFullPlanForUser(int idEmployee, int idPlan) throws Exception {
		Plan plan = planRepo.findByEmployee_IdEmployeeAndIdPlan(idEmployee,idPlan);
	    if(plan == null) {
	    	throw new Exception("All plans currently is closed");
	    }
	    
	    FullPlanDTO dto = modelMapper.map(plan, FullPlanDTO.class);
	    
	    // PROJECTS
	    ArrayList<ProjectPlanResponseDTO> projects =
	            projectPlanRepo.findByPlan_IdPlanAndDeletedFalse(plan.getIdPlan())
	                    .stream()
	                    .map(pp -> modelMapper.map(pp.getProject(), ProjectPlanResponseDTO.class))
	                    .collect(Collectors.toCollection(ArrayList::new));
	    
	    for(ProjectPlanResponseDTO temp:projects) {
	    	temp.setTasks(projectPlanRepo.findByPlan_IdPlanAndProject_IdProject(plan.getIdPlan(), temp.getIdProject()).getTasks());
			temp.setWorkDone(projectPlanRepo.findByPlan_IdPlanAndProject_IdProject(plan.getIdPlan(), temp.getIdProject()).getWorkDone());
		}

	    // ARTICLES
	    ArrayList<ScientificArticlesResponseDTO> articles =
	            articlePlanRepo.findByPlan_IdPlanAndDeletedFalse(plan.getIdPlan())
	                    .stream()
	                    .filter(pp -> !pp.getScientificArticles().isDeleted())
	                    .map(ap -> modelMapper.map(ap.getScientificArticles(), ScientificArticlesResponseDTO.class))
	                    .collect(Collectors.toCollection(ArrayList::new));
	    
	    for(ScientificArticlesResponseDTO temp:articles) {
		   temp.setArticleComments(articlePlanRepo.findByPlan_IdPlanAndScientificArticles_IdArticle(plan.getIdPlan(),
				   temp.getIdArticle()).getArticleComments());
		   temp.setPublicationLink(articlePlanRepo.findByPlan_IdPlanAndScientificArticles_IdArticle(plan.getIdPlan(),
				   temp.getIdArticle()).getPublicationLink());
	   }

	    // COURSES
	    ArrayList<CoursePlanResponseDTO> courses =
	            coursePlanRepo.findByPlan_IdPlanAndDeletedFalse(plan.getIdPlan())
	                    .stream()
	                    .filter(pp -> !pp.getCourse().isDeleted())
	                    .map(cp -> modelMapper.map(cp.getCourse(), CoursePlanResponseDTO.class))
	                    .collect(Collectors.toCollection(ArrayList::new));
	   
	   for(CoursePlanResponseDTO temp:courses) {
		   temp.setWorkDone(coursePlanRepo.findByPlan_IdPlanAndCourse_IdCourse(plan.getIdPlan(), temp.getIdCourse()).getWorkDone());
	   }

	    // STUDENT WORK
	    ArrayList<WorkPlanResponseDTO> studentWork =
	            studentWorkPlanRepo.findByPlan_IdPlanAndDeletedFalse(plan.getIdPlan())
	                    .stream()
	                    .filter(pp -> !pp.getStudentWork().isDeleted())
	                    .map(sw -> modelMapper.map(sw.getStudentWork(), WorkPlanResponseDTO.class))
	                    .collect(Collectors.toCollection(ArrayList::new));
	    
	    for(WorkPlanResponseDTO temp:studentWork) {
		   temp.setWorkDone(studentWorkPlanRepo.findByPlan_IdPlanAndStudentWork_IdStudWork(plan.getIdPlan(), temp.getIdStudWork()).getWorkDone());
	   }

	    dto.setProjects(projects);
	    dto.setArticles(articles);
	    dto.setCourses(courses);
	    dto.setStudentWork(studentWork);
	    
		return dto;
	}

	@Override
	public void updatePlanForUserByOpenPlan(int idEmployee, int numOfProjects, int numOfArticles,
			String partInConf, String partInConfEnd, String comAbConf, String comAbConfEnd, int numOfCourses,
			int numOfStudWork, String promoOfResearch, String promoOfResearchEnd, String adminWork, String adminWorkEnd,
			String projApplicSub, String projApplicSubEnd, String skillsDevelopment, String skillsDevelopmentEnd,
			String participationInSeminars, String participationInSeminarsEnd, String otherJobs, String otherJobsEnd)
			throws Exception {
		
		//Finds current year and input year
		int currentYear = LocalDate.now().getYear();
		Year year = yearRepo.findByYearNumber(currentYear);
		
		//Find plan by year and idEmployee
		Plan plan = planRepo.findFirstByEmployee_IdEmployeeAndYear_IdYear(idEmployee,year.getIdYear());
	
    	if (plan == null) throw new 
    		Exception("Plan with (Year:" + currentYear + ") does not exist for current user");    	
    	
    	Employee employee = employeeRepo.findById(idEmployee).get();
        if(employee == null) {
        	throw new Exception("Employee not found");
        }
        
        if(year.getIdYear() == 0) {
        	throw new Exception("Year not found");
        }
        
        PlanStatus status = plan.getPlanStatus();
        
        //TODO: paskatīties optimizāciju
        if(status == PlanStatus.plan_open) {
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
        }else if (status == PlanStatus.planned_frozen) {
            plan.setPartInConfEnd(partInConfEnd);
            plan.setComAbConfEnd(comAbConfEnd);
            plan.setPromoOfResearchEnd(promoOfResearchEnd);
            plan.setAdminWorkEnd(adminWorkEnd);
            plan.setProjApplicSubEnd(projApplicSubEnd);
            plan.setSkillsDevelopmentEnd(skillsDevelopmentEnd);
            plan.setParticipationInSeminarsEnd(participationInSeminarsEnd);
            plan.setOtherJobsEnd(otherJobsEnd);
            planRepo.save(plan);
        }else {
        	throw new Exception("Current plan is closed");
        }
	
		
	}

}

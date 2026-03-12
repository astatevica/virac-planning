package lv.venta.virac.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lv.venta.virac.dto.CourseDTO;
import lv.venta.virac.dto.CoursePlanDTO;
import lv.venta.virac.dto.FullPlanDTO;
import lv.venta.virac.dto.PlanDTO;
import lv.venta.virac.dto.ProjectDTO;
import lv.venta.virac.dto.ProjectPlanDTO;
import lv.venta.virac.dto.UpdatePlanDTO;
import lv.venta.virac.model.Course;
import lv.venta.virac.model.Plan;
import lv.venta.virac.model.Project;
import lv.venta.virac.model.ProjectPlan;
import lv.venta.virac.service.ICRUDCoursePlanService;
import lv.venta.virac.service.ICRUDCourseService;
import lv.venta.virac.service.ICRUDPlanService;
import lv.venta.virac.service.ICRUDProjectPlanService;
import lv.venta.virac.service.ICRUDProjectService;
import lv.venta.virac.user.User;


@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private ICRUDPlanService planService;
	private ICRUDProjectPlanService projPlanService;
	private ICRUDProjectService projService;
	private ICRUDCourseService courseService;
	private ICRUDCoursePlanService coursePlanService;
	
	public UserController(ICRUDPlanService planService, ICRUDProjectPlanService projPlanService, 
			ICRUDProjectService projService, ICRUDCourseService courseService,ICRUDCoursePlanService coursePlanService) {
		this.planService = planService;
		this.projPlanService = projPlanService;
		this.projService = projService;
		this.courseService = courseService;
		this.coursePlanService = coursePlanService;
	}
	
	@GetMapping("/filter/plans/all")
	@PreAuthorize("hasRole('USER')")
    public ResponseEntity<ArrayList<PlanDTO>> selectAllPlansByEmployee(
    		Authentication authentication) throws Exception {

		User user = (User) authentication.getPrincipal();
	    int employeeId = user.getEmployee().getIdEmployee();
		
        ArrayList<Plan> plans =
                planService.selectAllPlansByEmployee(employeeId);

        ArrayList<PlanDTO> response =
        	    new ArrayList<>(
        	    		plans.stream()
        	            .map(pl -> new PlanDTO(
        	               pl.getIdPlan(), pl.getEmployee().getIdEmployee(), pl.getYear().getIdYear(),pl.getNumOfProjects(),
        	               pl.getNumOfArticles(),pl.getPartInConf(),pl.getPartInConfEnd(),pl.getComAbConf(),
        	               pl.getComAbConfEnd(),pl.getNumOfCourses(),pl.getNumOfStudWork(),pl.getPromoOfResearch(),
        	               pl.getPromoOfResearchEnd(),pl.getAdminWork(),pl.getAdminWorkEnd(), pl.getProjApplicSub(),
        	               pl.getProjApplicSubEnd(),pl.getSkillsDevelopment(),pl.getSkillsDevelopmentEnd(),
        	               pl.getParticipationInSeminars(),pl.getParticipationInSeminarsEnd(),pl.getOtherJobs(),pl.getOtherJobsEnd()
        	            ))
        	            .toList()
        	    );
        return ResponseEntity.ok(response);
    }
	
	@GetMapping("/filter/plans/{idYear}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ArrayList<PlanDTO>> selectPlansByLoggedUserAndYear(
	        @PathVariable("idYear") int idYear,
	        Authentication authentication) throws Exception {

	    User user = (User) authentication.getPrincipal(); 
	    int employeeId = user.getEmployee().getIdEmployee();

	    ArrayList<Plan> plans = planService.selectAllPlansByEmployeeAndYear(employeeId, idYear);
	    
	    ArrayList<PlanDTO> response =
        	    new ArrayList<>(
        	    		plans.stream()
        	            .map(pl -> new PlanDTO(
        	               pl.getIdPlan(), pl.getEmployee().getIdEmployee(), pl.getYear().getIdYear(),pl.getNumOfProjects(),
        	               pl.getNumOfArticles(),pl.getPartInConf(),pl.getPartInConfEnd(),pl.getComAbConf(),
        	               pl.getComAbConfEnd(),pl.getNumOfCourses(),pl.getNumOfStudWork(),pl.getPromoOfResearch(),
        	               pl.getPromoOfResearchEnd(),pl.getAdminWork(),pl.getAdminWorkEnd(), pl.getProjApplicSub(),
        	               pl.getProjApplicSubEnd(),pl.getSkillsDevelopment(),pl.getSkillsDevelopmentEnd(),
        	               pl.getParticipationInSeminars(),pl.getParticipationInSeminarsEnd(),pl.getOtherJobs(),pl.getOtherJobsEnd()
        	            ))
        	            .toList()
        	    );
	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/plans/project/{projectId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ArrayList<PlanDTO>> getPlansByProject(
	        @PathVariable("projectId") int projectId, Authentication authentication) throws Exception {

	    User user = (User) authentication.getPrincipal();
	    int employeeId = user.getEmployee().getIdEmployee();
	    
	    ArrayList<Plan> plans = planService.selectAllPlansByEmployeeAndProject(employeeId, projectId);

	    ArrayList<PlanDTO> response =
        	    new ArrayList<>(
        	    		plans.stream()
        	            .map(pl -> new PlanDTO(
        	               pl.getIdPlan(), pl.getEmployee().getIdEmployee(), pl.getYear().getIdYear(),pl.getNumOfProjects(),
        	               pl.getNumOfArticles(),pl.getPartInConf(),pl.getPartInConfEnd(),pl.getComAbConf(),
        	               pl.getComAbConfEnd(),pl.getNumOfCourses(),pl.getNumOfStudWork(),pl.getPromoOfResearch(),
        	               pl.getPromoOfResearchEnd(),pl.getAdminWork(),pl.getAdminWorkEnd(), pl.getProjApplicSub(),
        	               pl.getProjApplicSubEnd(),pl.getSkillsDevelopment(),pl.getSkillsDevelopmentEnd(),
        	               pl.getParticipationInSeminars(),pl.getParticipationInSeminarsEnd(),pl.getOtherJobs(),pl.getOtherJobsEnd()
        	            ))
        	            .toList()
        	    );
	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/project-plan/filter/plan/{idPlan}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ArrayList<ProjectPlanDTO>> selectAllProjectPlanByPlan(
            @PathVariable("idPlan") int idPlan) throws Exception {

        ArrayList<ProjectPlan> projectPlans =
                projPlanService.selectAllProjectPlanByPlan(idPlan);
        
        if (projectPlans == null) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        ArrayList<ProjectPlanDTO> response = new ArrayList<>(
        		projectPlans.stream()
	            .map(projPlan -> new ProjectPlanDTO(
	            	projPlan.getIdProjectPlan(),
	            	projPlan.getPlan().getIdPlan(),
	            	projPlan.getProject().getIdProject(),
	            	projPlan.getTasks(),
	            	projPlan.getWorkDone()
	            ))
	            .toList());
        
        return ResponseEntity.ok(response);
    }
	
	@GetMapping("/all/projects")
	@PreAuthorize("hasRole('USER')")
    public ResponseEntity<ArrayList<ProjectDTO>> getAll()
            throws Exception {

        ArrayList<Project> list = projService.retrieveAll();

        ArrayList<ProjectDTO> response =
	            new ArrayList<>(list.stream()
	                .map(pr -> new ProjectDTO(
	                		pr.getIdProject(),
	                		pr.getName(),
	                		pr.getNumber(),
	                        pr.getProjectManagement().getIdProjectManag(),
	                        pr.getStartDate(),
	                        pr.getEndDate(),
	                        pr.getAcronym()
	                ))
	                .toList());

	    return ResponseEntity.ok(response);
    }
	
	@GetMapping("/plan/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PlanDTO> getById(
            @PathVariable("id") int id) throws Exception {

        Plan pl = planService.retrieveById(id);
        System.out.println(pl);
        return ResponseEntity.ok(
        		new PlanDTO(
     	               pl.getIdPlan(), pl.getEmployee().getIdEmployee(), pl.getYear().getIdYear(),pl.getNumOfProjects(),
     	               pl.getNumOfArticles(),pl.getPartInConf(),pl.getPartInConfEnd(),pl.getComAbConf(),
     	               pl.getComAbConfEnd(),pl.getNumOfCourses(),pl.getNumOfStudWork(),pl.getPromoOfResearch(),
     	               pl.getPromoOfResearchEnd(),pl.getAdminWork(),pl.getAdminWorkEnd(), pl.getProjApplicSub(),
     	               pl.getProjApplicSubEnd(),pl.getSkillsDevelopment(),pl.getSkillsDevelopmentEnd(),
     	               pl.getParticipationInSeminars(),pl.getParticipationInSeminarsEnd(),pl.getOtherJobs(),pl.getOtherJobsEnd()
     	            ));
    }
	
	@GetMapping("/full-plan/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<FullPlanDTO> getFullPlan(@PathVariable("id") int idPlan, Authentication authentication) throws Exception {

	    User user = (User) authentication.getPrincipal();
	    int employeeId = user.getEmployee().getIdEmployee();

	    FullPlanDTO dto = planService.getFullPlanForUser(employeeId, idPlan);

	    return ResponseEntity.ok(dto);
	}
	
	//Update planDTO 
	@PutMapping("/update/current-year/plan")
    public ResponseEntity<Void> updatePlanForUserByOpenPlan(@Valid @RequestBody UpdatePlanDTO pl,
            BindingResult result, Authentication authentication) throws Exception {
			
		//User user = (User) authentication.getPrincipal();
	    int employeeId = 5;//user.getEmployee().getIdEmployee();

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        planService.updatePlanForUserByOpenPlan(employeeId,pl.getNumOfProjects(),
	               pl.getNumOfArticles(),pl.getPartInConf(),pl.getPartInConfEnd(),pl.getComAbConf(),
	               pl.getComAbConfEnd(),pl.getNumOfCourses(),pl.getNumOfStudWork(),pl.getPromoOfResearch(),
	               pl.getPromoOfResearchEnd(),pl.getAdminWork(),pl.getAdminWorkEnd(), pl.getProjApplicSub(),
	               pl.getProjApplicSubEnd(),pl.getSkillsDevelopment(),pl.getSkillsDevelopmentEnd(),
	               pl.getParticipationInSeminars(),pl.getParticipationInSeminarsEnd(),pl.getOtherJobs(),pl.getOtherJobsEnd());
        return ResponseEntity.ok().build();
    }
	
	
	//Gets from frontend autocomplete for course name
	@GetMapping("/courses/autocomplete/{keyword}")
	public ResponseEntity<ArrayList<CourseDTO>> selectNameAutocomplete(@PathVariable("keyword") String keyword) throws Exception{
		
		ArrayList<Course> list = courseService.selectNameAutocomplete(keyword);
		System.out.println("UserController_1: " + list);
		ArrayList<CourseDTO> response =
	            new ArrayList<>(list.stream()
	                .map(dto -> new CourseDTO(
	                		dto.getIdCourse(),
	                		dto.getName(),
	                		dto.getEctsCredits(),
	                		dto.getSemester(),
	                		dto.getFaculty()	
	                ))
	                .toList());
		
		System.out.println("UserController_2: "+ response);
	    return ResponseEntity.ok(response);
	   		
	}
	
	//TODO: vai tas nebūs tas pats kas create?
	//Gets from frontend autocomplete course and plan to save in repo
	@GetMapping("/courses/autocomplete/{idCourse}/{idPlan}/{workDone}")
	public ResponseEntity<Void> saveCoursePlan(@PathVariable("idCourse") int idCourse,@PathVariable("idPlan") int idPlan,
	        @PathVariable("workDone") String workDone) throws Exception{
		System.out.println(" idPlan: " + idPlan + " idCourse: " + idCourse + " WorkDone: " + workDone);
		coursePlanService.create(idPlan, idCourse, workDone);

        return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	//Post endpoint to get all new course data and idPlan to save in repo
	@PostMapping("/add/course/{idPlan}/{workDone}")
	public ResponseEntity<CoursePlanDTO> createCourseForPlan(@PathVariable("idPlan") int idPlan, 
			@PathVariable("workDone") String workDone, @RequestBody CourseDTO courseDTO) throws Exception{
		CoursePlanDTO result = coursePlanService.createCourseAndAttachToPlan(idPlan, courseDTO, workDone);
	    return ResponseEntity.ok(result);
	}
	
	//Delete endpoint for course-plan deleting
	@DeleteMapping("/delete/course-plan/{idPlan}/{idCourse}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Void> deleteCoursePlan(@PathVariable("idPlan") int idPlan, 
			@PathVariable("idCourse") int idCourse) throws Exception {
	    coursePlanService.deleteByCourseIdAndPlanId(idPlan, idCourse);
	    return ResponseEntity.ok().build();
	}
	
	//Update endpoint for course-plan edit
	@PutMapping("/update/course-plan/{idPlan}/{idCourse}/{workDone}")
    public ResponseEntity<Void> update(@PathVariable("idPlan") int idPlan,@PathVariable("idCourse") int idCourse,
    		@PathVariable("workDone") String workDone,@Valid @RequestBody CoursePlanDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        coursePlanService.updateByCourseIdAndPlanId(idPlan,idCourse,workDone);
        return ResponseEntity.ok().build();
    }

}

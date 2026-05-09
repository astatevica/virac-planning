package lv.venta.virac.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import lv.venta.virac.dto.ArticleDTO;
import lv.venta.virac.dto.ArticlePlanCommentsDTO;
import lv.venta.virac.dto.ArticlePlanReponseDTO;
import lv.venta.virac.dto.CourseAutocompleteDTO;
import lv.venta.virac.dto.CourseDTO;
import lv.venta.virac.dto.CoursePlanResponseDTO;
import lv.venta.virac.dto.CoursePlanWorkDTO;
import lv.venta.virac.dto.FullPlanDTO;
import lv.venta.virac.dto.JournalDTO;
import lv.venta.virac.dto.JournalResponseDTO;
import lv.venta.virac.dto.PlanDTO;
import lv.venta.virac.dto.ProjectAutocompleteDTO;
import lv.venta.virac.dto.ProjectDTO;
import lv.venta.virac.dto.ProjectPlanDTO;
import lv.venta.virac.dto.ProjectPlanResponseDTO;
import lv.venta.virac.dto.ScientificArticlesCommentsDTO;
import lv.venta.virac.dto.StudentWorkPlanResponseDTO;
import lv.venta.virac.dto.UpdatePlanDTO;
import lv.venta.virac.dto.WorkPlanResponseDTO;
import lv.venta.virac.errors.ErrorResponse;
import lv.venta.virac.errors.FieldErrorDetail;
import lv.venta.virac.model.Course;
import lv.venta.virac.model.Journal;
import lv.venta.virac.model.Plan;
import lv.venta.virac.model.Project;
import lv.venta.virac.model.ProjectPlan;
import lv.venta.virac.model.ScientificArticles;
import lv.venta.virac.scheduler.ICRUDPlanSchedulerService;
import lv.venta.virac.scheduler.SchedulerDTO;
import lv.venta.virac.service.ICRUDArticlePlanService;
import lv.venta.virac.service.ICRUDCoursePlanService;
import lv.venta.virac.service.ICRUDCourseService;
import lv.venta.virac.service.ICRUDJournalService;
import lv.venta.virac.service.ICRUDPlanService;
import lv.venta.virac.service.ICRUDProjectPlanService;
import lv.venta.virac.service.ICRUDProjectService;
import lv.venta.virac.service.ICRUDScientificArticlesService;
import lv.venta.virac.service.ICRUDWorkPlanService;
import lv.venta.virac.user.User;


@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private ICRUDPlanService planService;
	private ICRUDProjectPlanService projPlanService;
	private ICRUDProjectService projService;
	private ICRUDCourseService courseService;
	private ICRUDCoursePlanService coursePlanService;
	private ICRUDScientificArticlesService articlesService;
	private ICRUDArticlePlanService artPlanService;
	private ICRUDJournalService journalService;
	private ICRUDWorkPlanService studentWorkService;
	private ICRUDPlanSchedulerService schedulerService;
	
	public UserController(ICRUDPlanService planService, ICRUDProjectPlanService projPlanService, 
			ICRUDProjectService projService, ICRUDCourseService courseService,ICRUDCoursePlanService coursePlanService,
			ICRUDScientificArticlesService articlesService, ICRUDArticlePlanService artPlanService, ICRUDJournalService journalService,
			ICRUDWorkPlanService studentWorkService,ICRUDPlanSchedulerService schedulerService) {
		this.planService = planService;
		this.projPlanService = projPlanService;
		this.projService = projService;
		this.courseService = courseService;
		this.coursePlanService = coursePlanService;
		this.articlesService = articlesService;
		this.artPlanService = artPlanService;
		this.journalService = journalService;
		this.studentWorkService = studentWorkService;
		this.schedulerService = schedulerService;
	}

	@GetMapping("/filter/plans/all")
	@PreAuthorize("hasRole('USER')")
    public ResponseEntity<ArrayList<PlanDTO>> selectAllPlansByEmployee(
    		Authentication authentication){
		try {
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
        }catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).build();
	    }
    }
	
	@GetMapping("/filter/plans/{idYear}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ArrayList<PlanDTO>> selectPlansByLoggedUserAndYear(
	        @PathVariable("idYear") int idYear,
	        Authentication authentication){
		try {
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
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }
	}
	
	@GetMapping("/plans/project/{projectId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ArrayList<PlanDTO>> getPlansByProject(
	        @PathVariable("projectId") int projectId, Authentication authentication){
		try {
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
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }
	}
	
	@GetMapping("/project-plan/filter/plan/{idPlan}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ArrayList<ProjectPlanDTO>> selectAllProjectPlanByPlan(
            @PathVariable("idPlan") int idPlan){
		try {
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
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }
    }
	
	@GetMapping("/all/projects")
	@PreAuthorize("hasRole('USER')")
    public ResponseEntity<ArrayList<ProjectDTO>> getAll(){
		try {
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
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }
    }
	
	@GetMapping("/plan/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PlanDTO> getById(
            @PathVariable("id") int id){
		try {
	        Plan pl = planService.retrieveById(id);
	        return ResponseEntity.ok(
	        		new PlanDTO(
	     	               pl.getIdPlan(), pl.getEmployee().getIdEmployee(), pl.getYear().getIdYear(),pl.getNumOfProjects(),
	     	               pl.getNumOfArticles(),pl.getPartInConf(),pl.getPartInConfEnd(),pl.getComAbConf(),
	     	               pl.getComAbConfEnd(),pl.getNumOfCourses(),pl.getNumOfStudWork(),pl.getPromoOfResearch(),
	     	               pl.getPromoOfResearchEnd(),pl.getAdminWork(),pl.getAdminWorkEnd(), pl.getProjApplicSub(),
	     	               pl.getProjApplicSubEnd(),pl.getSkillsDevelopment(),pl.getSkillsDevelopmentEnd(),
	     	               pl.getParticipationInSeminars(),pl.getParticipationInSeminarsEnd(),pl.getOtherJobs(),pl.getOtherJobsEnd()
	     	            ));
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).build();
	    }
    }
	
	@GetMapping("/full-plan/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<FullPlanDTO> getFullPlan(@PathVariable("id") int idPlan, Authentication authentication){
		try {
		    User user = (User) authentication.getPrincipal();
		    int employeeId = user.getEmployee().getIdEmployee();
	
		    FullPlanDTO dto = planService.getFullPlanForUser(employeeId, idPlan);
	
		    return ResponseEntity.ok(dto);
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).build();
	    }
	}
	
	//Update planDTO 
	@PutMapping("/update/current-year/plan")
    public ResponseEntity<?> updatePlanForUserByOpenPlan(@Valid @RequestBody UpdatePlanDTO pl,
            BindingResult result, Authentication authentication){
		try {
			User user = (User) authentication.getPrincipal();
		    int employeeId = user.getEmployee().getIdEmployee();
	
		    if (result.hasErrors()) {
	            // Convert FieldErrors to FieldErrorDetail objects
	            List<FieldErrorDetail> errors = result.getFieldErrors().stream()
	                    .map(error -> new FieldErrorDetail(
	                            error.getField(),
	                            error.getDefaultMessage(),
	                            error.getRejectedValue()
	                    ))
	                    .collect(Collectors.toList());
	 
	            // Create ErrorResponse
	            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
	 
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	        }
	
	        planService.updatePlanForUserByOpenPlan(employeeId,pl.getNumOfProjects(),
		               pl.getNumOfArticles(),pl.getPartInConf(),pl.getPartInConfEnd(),pl.getComAbConf(),
		               pl.getComAbConfEnd(),pl.getNumOfCourses(),pl.getNumOfStudWork(),pl.getPromoOfResearch(),
		               pl.getPromoOfResearchEnd(),pl.getAdminWork(),pl.getAdminWorkEnd(), pl.getProjApplicSub(),
		               pl.getProjApplicSubEnd(),pl.getSkillsDevelopment(),pl.getSkillsDevelopmentEnd(),
		               pl.getParticipationInSeminars(),pl.getParticipationInSeminarsEnd(),pl.getOtherJobs(),pl.getOtherJobsEnd());
	        return ResponseEntity.ok().build();
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }
    }
	
	//---------------------------- COURSES SECTION -------------------------------------//
	
	//SELECT Gets from frontend autocomplete for course name
	@GetMapping("/courses/autocomplete/{keyword}")
	public ResponseEntity<ArrayList<CourseDTO>> selectNameAutocomplete(@PathVariable("keyword") String keyword){
		
		try {
			ArrayList<Course> list = courseService.selectNameAutocomplete(keyword);
			
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
			
		    return ResponseEntity.ok(response);
		}catch(Exception e){
	        e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
		}
	   		
	}
	
	//SAVE Gets from frontend autocomplete course and plan to save in repo
	@GetMapping("/courses/autocomplete")
	public ResponseEntity<?> saveCoursePlan(@Valid CourseAutocompleteDTO dto, BindingResult result,
			Authentication authentication){
		
		if (result.hasErrors()) {
            // Convert FieldErrors to FieldErrorDetail objects
            List<FieldErrorDetail> errors = result.getFieldErrors().stream()
                    .map(error -> new FieldErrorDetail(
                            error.getField(),
                            error.getDefaultMessage(),
                            error.getRejectedValue()
                    ))
                    .collect(Collectors.toList());
            // Create ErrorResponse
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
		
		User user = (User) authentication.getPrincipal();
	    int employeeId = user.getEmployee().getIdEmployee();
	    try {
			coursePlanService.createAutocompleteCourse(dto.getIdPlan(), dto.getIdCourse(), dto.getWorkDone(), employeeId);
			return ResponseEntity.status(HttpStatus.CREATED).build();
	    }catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }
	}

	//CREATE Post endpoint to get all new course data and idPlan to save in repo
	@PostMapping("/add/course/{idPlan}")
	public ResponseEntity<?> createCourseForPlan(@PathVariable("idPlan") int idPlan, 
			@Valid @RequestBody CoursePlanWorkDTO courseDTO, BindingResult result, 
			Authentication authentication){
		
		User user = (User) authentication.getPrincipal();
	    int employeeId = user.getEmployee().getIdEmployee();
	    
	    if (result.hasErrors()) {
            // Convert FieldErrors to FieldErrorDetail objects
            List<FieldErrorDetail> errors = result.getFieldErrors().stream()
                    .map(error -> new FieldErrorDetail(
                            error.getField(),
                            error.getDefaultMessage(),
                            error.getRejectedValue()
                    ))
                    .collect(Collectors.toList());
            // Create ErrorResponse
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
	    
	    try {
		    CoursePlanResponseDTO resultReponse = coursePlanService.createCourseAndAttachToPlan(idPlan, courseDTO, employeeId);
		    return ResponseEntity.ok(resultReponse);
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }	    
	}
	
	//DELETE endpoint for course-plan deleting
	@DeleteMapping("/delete/course-plan/{idPlan}/{idCourse}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Void> deleteCoursePlan(@PathVariable("idPlan") int idPlan, 
			@PathVariable("idCourse") int idCourse, Authentication authentication){
		
		User user = (User) authentication.getPrincipal();
	    int employeeId = user.getEmployee().getIdEmployee();
	    try {
		    coursePlanService.deleteByCourseIdAndPlanId(idPlan, idCourse, employeeId);
		    return ResponseEntity.ok().build();
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).build();
	    }
	}
	
	//UPDATE endpoint for course-plan edit
	@PutMapping("/update/course-plan")
    public ResponseEntity<?> update(@Valid @RequestBody CoursePlanResponseDTO dto,
            BindingResult result, Authentication authentication){
		User user = (User) authentication.getPrincipal();
	    int employeeId = user.getEmployee().getIdEmployee();

	    if (result.hasErrors()) {
            // Convert FieldErrors to FieldErrorDetail objects
            List<FieldErrorDetail> errors = result.getFieldErrors().stream()
                    .map(error -> new FieldErrorDetail(
                            error.getField(),
                            error.getDefaultMessage(),
                            error.getRejectedValue()
                    ))
                    .collect(Collectors.toList());
            // Create ErrorResponse
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
	    try {
	        coursePlanService.updateByCourseIdAndPlanId(dto.getIdPlan(),dto.getIdCourse(),dto.getWorkDone(), employeeId);
	        return ResponseEntity.ok().build();
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }
    }
	
	//---------------------------- ARTICLE SECTION -------------------------------------//
	
	//GET Journals for dropdown list
	@GetMapping("/journals/all")
    public ResponseEntity<?> getAllJournals(@Valid JournalDTO dto, BindingResult result){
		
		try {
			//Do not need to check user
	        ArrayList<Journal> journals = journalService.retrieveAll();
	        
	        if (result.hasErrors()) {
	            // Convert FieldErrors to FieldErrorDetail objects
	            List<FieldErrorDetail> errors = result.getFieldErrors().stream()
	                    .map(error -> new FieldErrorDetail(
	                            error.getField(),
	                            error.getDefaultMessage(),
	                            error.getRejectedValue()
	                    ))
	                    .collect(Collectors.toList());
	            // Create ErrorResponse
	            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	        }
	
	        ArrayList<JournalDTO> response =
	        	    new ArrayList<>(
	        	    		journals.stream()
	        	            .map(jou -> new JournalDTO(
	        	            	jou.getIdJournal(),
	        	                jou.getName()
	        	            ))
	        	            .toList()
	        	    );
	        return ResponseEntity.ok(response);
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }
    }

	//GET Journal by id
    @GetMapping("/journals/{idJournal}")
    public ResponseEntity<JournalDTO> getJournalById(@PathVariable("idJournal") int idJournal){
    	try {
	    	//Do not need to check user
	        Journal jou = journalService.retrieveById(idJournal);
	        return ResponseEntity.ok(new JournalDTO(jou.getIdJournal(),jou.getName()));
	    }catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(null);
	    }
    }

    //CREATE new journal
    @PostMapping("/journals/add")
    public ResponseEntity<?> createNewJournal(@Valid @RequestBody JournalResponseDTO dto, BindingResult result){
    	try {
	    	if (result.hasErrors()) {
	            // Convert FieldErrors to FieldErrorDetail objects
	            List<FieldErrorDetail> errors = result.getFieldErrors().stream()
	                    .map(error -> new FieldErrorDetail(
	                            error.getField(),
	                            error.getDefaultMessage(),
	                            error.getRejectedValue()
	                    ))
	                    .collect(Collectors.toList());
	            // Create ErrorResponse
	            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	        }
	
	        journalService.create(dto.getName());
	        return ResponseEntity.status(HttpStatus.CREATED).build();
	    }catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }
    }
	
	//Gets from frontend autocomplete for article name
	@GetMapping("/article/autocomplete/{keyword}")
	public ResponseEntity<ArrayList<ArticleDTO>> selectArticleNameAutocomplete
		(@PathVariable("keyword") String keyword){
		
		try {
			ArrayList<ScientificArticles> list = articlesService.selectNameAutocomplete(keyword);
			ArrayList<ArticleDTO> response =
		            new ArrayList<>(list.stream()
		                .map(dto -> new ArticleDTO(
		                		dto.getIdArticle(),
		                		dto.getName(),
		                		dto.getCoAuthors(),
		                		dto.getJournal().getName()
		                ))
		                .toList());
			
		    return ResponseEntity.ok(response);
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(null);
	    }
	   		
	}
	
	//SAVE and gets from frontend autocomplete article and plan to save in repo
	@GetMapping("/articles/autocomplete/{idArticle}/{idPlan}")
	public ResponseEntity<?> saveArticlePlan(@PathVariable("idArticle") int idArticle ,@PathVariable("idPlan") int idPlan,
			@Valid ArticlePlanCommentsDTO dto, BindingResult result, Authentication authentication){
		try {
			User user = (User) authentication.getPrincipal();
		    int employeeId = user.getEmployee().getIdEmployee();
		    
		    if (result.hasErrors()) {
	            // Convert FieldErrors to FieldErrorDetail objects
	            List<FieldErrorDetail> errors = result.getFieldErrors().stream()
	                    .map(error -> new FieldErrorDetail(
	                            error.getField(),
	                            error.getDefaultMessage(),
	                            error.getRejectedValue()
	                    ))
	                    .collect(Collectors.toList());
	            // Create ErrorResponse
	            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	        }
		    
			artPlanService.createAutocompleteArticle(idPlan, idArticle, dto, employeeId);
	
	        return ResponseEntity.status(HttpStatus.CREATED).build();
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }
	}
	
	//CREATE endpoint to get all new article data and idPlan to save in repo
	@PostMapping("/add/article/{idPlan}")
	public ResponseEntity<?> createArticleForPlan(@PathVariable("idPlan") int idPlan, 
			@Valid @RequestBody ScientificArticlesCommentsDTO articleDTO, BindingResult result, 
			Authentication authentication) throws Exception{
		try {
			User user = (User) authentication.getPrincipal();
		    int employeeId = user.getEmployee().getIdEmployee();
		    
		    if (result.hasErrors()) {
	            // Convert FieldErrors to FieldErrorDetail objects
	            List<FieldErrorDetail> errors = result.getFieldErrors().stream()
	                    .map(error -> new FieldErrorDetail(
	                            error.getField(),
	                            error.getDefaultMessage(),
	                            error.getRejectedValue()
	                    ))
	                    .collect(Collectors.toList());
	 
	            // Create ErrorResponse
	            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
	 
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	        }
		    
		    ArticlePlanReponseDTO resultReponse = artPlanService.createArticleAndAttachToPlan(idPlan, articleDTO, employeeId);
			
		    return ResponseEntity.ok(resultReponse);
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }
	}
	
	//Delete endpoint for article-plan deleting
	@DeleteMapping("/delete/article-plan/{idPlan}/{idArticle}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Void> deleteArticlePlan(@PathVariable("idPlan") int idPlan, 
			@PathVariable("idArticle") int idArticle, Authentication authentication){
		try {
			User user = (User) authentication.getPrincipal();
		    int employeeId = user.getEmployee().getIdEmployee();
		    
		    artPlanService.deleteByArticleIdAndPlanId(idPlan, idArticle, employeeId);
		    return ResponseEntity.ok().build();
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).build();
	    }
	}

	//Update endpoint for course-plan edit
	@PutMapping("/update/article-plan")
    public ResponseEntity<?> update(@Valid @RequestBody ArticlePlanReponseDTO dto,
            BindingResult result, Authentication authentication){
		try {
			User user = (User) authentication.getPrincipal();
		    int employeeId = user.getEmployee().getIdEmployee();
	
		    if (result.hasErrors()) {
	            // Convert FieldErrors to FieldErrorDetail objects
	            List<FieldErrorDetail> errors = result.getFieldErrors().stream()
	                    .map(error -> new FieldErrorDetail(
	                            error.getField(),
	                            error.getDefaultMessage(),
	                            error.getRejectedValue()
	                    ))
	                    .collect(Collectors.toList());
	 
	            // Create ErrorResponse
	            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
	 
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	        }
		    
	        artPlanService.updateByArticleIdAndPlanId(dto.getIdPlan(),dto.getIdArticle(),dto.getArticleComments(), dto.getPublicationLink(), employeeId);
	        return ResponseEntity.ok().build();
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }
    }
	
	//---------------------------- STUDENT WORK SECTION -------------------------------------//
	
	//CREATE Post endpoint to get all new student work data and idPlan to save in repo
	@PostMapping("/add/work-plan/{idPlan}")
	public ResponseEntity<?> createStudentWorkForPlan(@PathVariable("idPlan") int idPlan, 
			@Valid @RequestBody WorkPlanResponseDTO dto, BindingResult result, 
			Authentication authentication){
		try {
			User user = (User) authentication.getPrincipal();
		    int employeeId = user.getEmployee().getIdEmployee();
		    
		    if (result.hasErrors()) {
	            // Convert FieldErrors to FieldErrorDetail objects
	            List<FieldErrorDetail> errors = result.getFieldErrors().stream()
	                    .map(error -> new FieldErrorDetail(
	                            error.getField(),
	                            error.getDefaultMessage(),
	                            error.getRejectedValue()
	                    ))
	                    .collect(Collectors.toList());
	            // Create ErrorResponse
	            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	        }
		    
		    StudentWorkPlanResponseDTO resultReponse = studentWorkService.createWorkAndAttachToPlan(idPlan, dto, employeeId);
			
		    return ResponseEntity.ok(resultReponse);
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }
	}
	
	//DELETE endpoint for work-plan deleting
	@DeleteMapping("/delete/work-plan/{idPlan}/{idStudWork}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Void> deleteWorkPlan(@PathVariable("idPlan") int idPlan, 
			@PathVariable("idStudWork") int idStudWork, Authentication authentication){
		try {
			User user = (User) authentication.getPrincipal();
		    int employeeId = user.getEmployee().getIdEmployee();
		    
		    studentWorkService.deleteByWorkIdAndPlanId(idPlan, idStudWork, employeeId);
		    return ResponseEntity.ok().build();
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).build();
	    }
	}
	
	//UPDATE endpoint for work-plan edit
	@PutMapping("/update/work-plan")
    public ResponseEntity<?> updateWorkPlan(@Valid @RequestBody StudentWorkPlanResponseDTO dto,
            BindingResult result, Authentication authentication){
		try {
			User user = (User) authentication.getPrincipal();
		    int employeeId = user.getEmployee().getIdEmployee();
	
		    if (result.hasErrors()) {
	            // Convert FieldErrors to FieldErrorDetail objects
	            List<FieldErrorDetail> errors = result.getFieldErrors().stream()
	                    .map(error -> new FieldErrorDetail(
	                            error.getField(),
	                            error.getDefaultMessage(),
	                            error.getRejectedValue()
	                    ))
	                    .collect(Collectors.toList());
	            // Create ErrorResponse
	            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	        }
		    
		    studentWorkService.updateByWorkIdAndPlanId(dto.getIdPlan(), dto.getIdStudWork(), dto.getWorkDone(), employeeId);
	        return ResponseEntity.ok().build();
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }
    }
	
	//---------------------------- PROJECT SECTION -------------------------------------//
	
	//SELECT Gets from frontend autocomplete for project name
	@GetMapping("/project/autocomplete/{keyword}")
	public ResponseEntity<ArrayList<ProjectDTO>> selectProjectNameAutocomplete(@PathVariable("keyword") String keyword){
		
		try {
			ArrayList<Project> list = projService.selectNameAutocomplete(keyword);
			
			ArrayList<ProjectDTO> response =
		            new ArrayList<>(list.stream()
		                .map(dto -> new ProjectDTO(
		                		dto.getIdProject(),
		                		dto.getName(),
		                		dto.getNumber(),
		                		dto.getProjectManagement().getIdProjectManag(),
		                		dto.getStartDate(),
		                		dto.getEndDate(),
		                		dto.getAcronym()
		                ))
		                .toList());
			
		    return ResponseEntity.ok(response);
		}catch(Exception e){
	        e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
		}
	   		
	}
	
	//SAVE Gets from frontend autocomplete project and plan to save in repo
	@GetMapping("/project/autocomplete")
	public ResponseEntity<?> saveProjectPlan(@Valid ProjectAutocompleteDTO dto, BindingResult result,
			Authentication authentication){
		
		if (result.hasErrors()) {
            // Convert FieldErrors to FieldErrorDetail objects
            List<FieldErrorDetail> errors = result.getFieldErrors().stream()
                    .map(error -> new FieldErrorDetail(
                            error.getField(),
                            error.getDefaultMessage(),
                            error.getRejectedValue()
                    ))
                    .collect(Collectors.toList());
            // Create ErrorResponse
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
		
		User user = (User) authentication.getPrincipal();
	    int employeeId = user.getEmployee().getIdEmployee();
	    try {
			projPlanService.createAutocompleteProject(dto.getIdPlan(), dto.getIdProject(),dto.getTasks(),dto.getWorkDone(), employeeId);
			return ResponseEntity.status(HttpStatus.CREATED).build();
	    }catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }
	}
	
	//DELETE endpoint for project-plan deleting
	@DeleteMapping("/delete/project-plan/{idPlan}/{idProject}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Void> deleteProjectPlan(@PathVariable("idPlan") int idPlan, 
			@PathVariable("idProject") int idProject, Authentication authentication){
		
		User user = (User) authentication.getPrincipal();
	    int employeeId = user.getEmployee().getIdEmployee();
	    try {
		    projPlanService.deleteByProjectIdAndPlanId(idPlan, idProject, employeeId);
		    return ResponseEntity.ok().build();
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).build();
	    }
	}
	
	//UPDATE endpoint for course-plan edit
	@PutMapping("/update/project-plan")
    public ResponseEntity<?> updateProejctPlan(@Valid @RequestBody ProjectPlanResponseDTO dto,
            BindingResult result, Authentication authentication){
		User user = (User) authentication.getPrincipal();
	    int employeeId = user.getEmployee().getIdEmployee();

	    if (result.hasErrors()) {
            // Convert FieldErrors to FieldErrorDetail objects
            List<FieldErrorDetail> errors = result.getFieldErrors().stream()
                    .map(error -> new FieldErrorDetail(
                            error.getField(),
                            error.getDefaultMessage(),
                            error.getRejectedValue()
                    ))
                    .collect(Collectors.toList());
            // Create ErrorResponse
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
	    try {
	        projPlanService.updateByProjectIdAndPlanId(dto.getIdPlan(),dto.getIdProject(),dto.getTasks(), dto.getWorkDone(), employeeId);
	        return ResponseEntity.ok().build();
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).body(new ArrayList<>());
	    }
    }
	//---------------------------- SCHEDULER SECTION -------------------------------------//
    //Scheduler endpoint
    @GetMapping("/scheduler/{idYear}")
    public ResponseEntity<SchedulerDTO> getSchedulerByYear(@PathVariable("idYear") int idYear, Authentication authentication){
    	try {
	
		    SchedulerDTO dto = schedulerService.getByYearId(idYear);
	
		    return ResponseEntity.ok(dto);
		}catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).build();
	    }
    }
}

package lv.venta.virac.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lv.venta.virac.dto.PlanDTO;
import lv.venta.virac.model.Plan;
import lv.venta.virac.service.ICRUDPlanService;
import lv.venta.virac.user.User;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private ICRUDPlanService planService;
	
	public UserController(ICRUDPlanService planService) {
		this.planService = planService;
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


	

}

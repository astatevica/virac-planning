package lv.venta.virac.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lv.venta.virac.dto.ProjectPlanDTO;
import lv.venta.virac.model.ProjectPlan;
import lv.venta.virac.service.ICRUDProjectPlanService;

@RestController
@RequestMapping("/api/admin/project-plan")
@CrossOrigin(origins = "http://localhost:3000")
public class CRUDProjectPlanController {
	
	private ICRUDProjectPlanService projPlanService;
	
	public CRUDProjectPlanController(ICRUDProjectPlanService projPlanService) {
		this.projPlanService = projPlanService;
	}

	@GetMapping
    public ResponseEntity<ArrayList<ProjectPlanDTO>> getAllProjectPlans() throws Exception {

        ArrayList<ProjectPlan> projectPlans = projPlanService.retrieveAll();

        ArrayList<ProjectPlanDTO> response =
        	    new ArrayList<>(
        	        projectPlans.stream()
        	            .map(projPlan -> new ProjectPlanDTO(
        	            	projPlan.getIdProjectPlan(),
        	            	projPlan.getPlan().getIdPlan(),
        	            	projPlan.getProject().getIdProject(),
        	            	projPlan.getTasks(),
        	            	projPlan.getWorkDone()
        	            ))
        	            .toList()
        	    );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProjectPlanDTO> getById(
            @PathVariable("id") int id) throws Exception {

        ProjectPlan projPlan = projPlanService.retrieveById(id);
        System.out.println(projPlan);
        return ResponseEntity.ok(
            new ProjectPlanDTO(projPlan.getIdProjectPlan(),projPlan.getPlan().getIdPlan(),
            		projPlan.getProject().getIdProject(), projPlan.getTasks(), projPlan.getWorkDone())
        );
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody ProjectPlanDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        projPlanService.create(dto.getIdPlan(),dto.getIdProject(),dto.getTasks(),dto.getWorkDone());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") int id,
            @Valid @RequestBody ProjectPlanDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        projPlanService.updateById(dto.getIdProjectPlan(),dto.getIdPlan(),dto.getIdProject(),dto.getTasks(),dto.getWorkDone());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) throws Exception {
        projPlanService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/filter/plan/{idPlan}")
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
    
    @GetMapping("/filter/project/{idProject}")
    public ResponseEntity<ArrayList<ProjectPlanDTO>> selectAllProjectPlanByProject(
            @PathVariable("idProject") int idProject) throws Exception {

        ArrayList<ProjectPlan> projectPlans =
                projPlanService.selectAllProjectPlanByProject(idProject);

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
}

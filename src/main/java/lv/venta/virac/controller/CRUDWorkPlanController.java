package lv.venta.virac.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import lv.venta.virac.dto.WorkPlanDTO;
import lv.venta.virac.model.WorkPlan;
import lv.venta.virac.service.ICRUDWorkPlanService;

@RestController
@RequestMapping("/api/work-plan")
@CrossOrigin(origins = "http://localhost:3000")
public class CRUDWorkPlanController {
	
	private ICRUDWorkPlanService wpService;
	
	public CRUDWorkPlanController(ICRUDWorkPlanService wpService) {
		this.wpService = wpService;
	}
	
	@GetMapping
    public ResponseEntity<ArrayList<WorkPlanDTO>> getAllWorkPlan() throws Exception {

        ArrayList<WorkPlan> workPlan = wpService.retrieveAll();

        ArrayList<WorkPlanDTO> response =
        	    new ArrayList<>(
        	    	workPlan.stream()
        	            .map(wp -> new WorkPlanDTO(
        	                wp.getIdWorkPlan(),
        	                wp.getStudentWork().getIdStudWork(),
        	                wp.getPlan().getIdPlan(),
        	                wp.getWorkDone()
        	            ))
        	            .toList()
        	    );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<WorkPlanDTO> getById(
            @PathVariable("id") int id) throws Exception {

        WorkPlan wp = wpService.retrieveById(id);
        System.out.println(wp);
        return ResponseEntity.ok(
        		new WorkPlanDTO(
    	                wp.getIdWorkPlan(),
    	                wp.getStudentWork().getIdStudWork(),
    	                wp.getPlan().getIdPlan(),
    	                wp.getWorkDone()));
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody WorkPlanDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        wpService.create(dto.getIdStudWork(), dto.getIdPlan(), dto.getWorkDone());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") int id,
            @Valid @RequestBody WorkPlanDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        wpService.updateById(id, dto.getIdStudWork(), dto.getIdPlan(), dto.getWorkDone());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) throws Exception {
        wpService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/filter/student-work/{idStudentWork}")
    public ResponseEntity<ArrayList<WorkPlanDTO>> selectAllWorkPlanByStudentWork(
            @PathVariable("idStudentWork") int idStudentWork) throws Exception {

        ArrayList<WorkPlan> workPlans =
                wpService.selectAllWorkPlanByStudentWork(idStudentWork);

        ArrayList<WorkPlanDTO> response =
        	    new ArrayList<>(
        	    	workPlans.stream()
        	            .map(wp -> new WorkPlanDTO(
        	                wp.getIdWorkPlan(),
        	                wp.getStudentWork().getIdStudWork(),
        	                wp.getPlan().getIdPlan(),
        	                wp.getWorkDone()
        	            ))
        	            .toList());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/filter/plan/{idPlan}")
    public ResponseEntity<ArrayList<WorkPlanDTO>> selectAllWorkPlanByPlan(
            @PathVariable("idPlan") int idPlan) throws Exception {

        ArrayList<WorkPlan> workPlans =
                wpService.selectAllWorkPlanByPlan(idPlan);

        ArrayList<WorkPlanDTO> response =
        	    new ArrayList<>(
        	    	workPlans.stream()
        	            .map(wp -> new WorkPlanDTO(
        	                wp.getIdWorkPlan(),
        	                wp.getStudentWork().getIdStudWork(),
        	                wp.getPlan().getIdPlan(),
        	                wp.getWorkDone()
        	            ))
        	            .toList());
        
        return ResponseEntity.ok(response);
    }
    

}

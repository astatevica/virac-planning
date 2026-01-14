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
import lv.venta.virac.dto.CoursePlanDTO;
import lv.venta.virac.model.CoursePlan;
import lv.venta.virac.service.ICRUDCoursePlanService;

@RestController
@RequestMapping("/api/course-plan")
@CrossOrigin(origins = "http://localhost:3000")
public class CRUDCoursePlanController {
	
	private ICRUDCoursePlanService coursePlanService;
	
	public CRUDCoursePlanController(ICRUDCoursePlanService coursePlanService) {
		this.coursePlanService = coursePlanService;
	}

	@GetMapping
    public ResponseEntity<ArrayList<CoursePlanDTO>> getAll() throws Exception {

        ArrayList<CoursePlan> coursePlans = coursePlanService.retrieveAll();

        ArrayList<CoursePlanDTO> response =
        	    new ArrayList<>(
        	    	coursePlans.stream()
        	            .map(cp -> new CoursePlanDTO(
        	               cp.getIdCoursePlan(),
        	               cp.getCourse().getIdCourse(),
        	               cp.getPlan().getIdPlan(),
        	               cp.getWorkDone()
        	            ))
        	            .toList()
        	    );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CoursePlanDTO> getById(
            @PathVariable("id") int id) throws Exception {

    	CoursePlan cp = coursePlanService.retrieveById(id);
        System.out.println(cp);
        return ResponseEntity.ok(
        		new CoursePlanDTO(
     	               cp.getIdCoursePlan(),
     	               cp.getCourse().getIdCourse(),
     	               cp.getPlan().getIdPlan(),
     	               cp.getWorkDone())
        );
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody CoursePlanDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        coursePlanService.create(dto.getIdPlan(),dto.getIdCourse(), dto.getWorkDone());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") int id,
            @Valid @RequestBody CoursePlanDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        coursePlanService.updateById(id, dto.getIdPlan(),dto.getIdCourse(), dto.getWorkDone());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) throws Exception {
    	coursePlanService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/filter/{idPlan}")
    public ResponseEntity<ArrayList<CoursePlanDTO>> selectAllArticlePlanByPlan(
            @PathVariable("idPlan") int idPlan) throws Exception {

        ArrayList<CoursePlan> coursePlans =
                coursePlanService.selectAllCoursePlanByPlan(idPlan);

        ArrayList<CoursePlanDTO> response =
        	    new ArrayList<>(
        	    	coursePlans.stream()
        	            .map(cp -> new CoursePlanDTO(
        	               cp.getIdCoursePlan(),
        	               cp.getCourse().getIdCourse(),
        	               cp.getPlan().getIdPlan(),
        	               cp.getWorkDone()
        	            ))
        	            .toList()
        	    );
        return ResponseEntity.ok(response);
    }
}

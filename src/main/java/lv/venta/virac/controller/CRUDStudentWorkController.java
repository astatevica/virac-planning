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
import lv.venta.virac.dto.StudentWorkDTO;
import lv.venta.virac.model.StudentWork;
import lv.venta.virac.service.ICRUDStudentWorkService;

@RestController
@RequestMapping("/api/student-work")
@CrossOrigin(origins = "http://localhost:3000")
public class CRUDStudentWorkController {
	
	private ICRUDStudentWorkService swService;
	
	public CRUDStudentWorkController(ICRUDStudentWorkService swService) {
		this.swService = swService;
	}
	
	@GetMapping
    public ResponseEntity<ArrayList<StudentWorkDTO>> getAllStudentWork() throws Exception {

        ArrayList<StudentWork> studentWork = swService.retrieveAll();

        ArrayList<StudentWorkDTO> response =
        	    new ArrayList<>(
        	    	studentWork.stream()
        	            .map(sw -> new StudentWorkDTO(
        	                sw.getIdStudWork(),
        	                sw.getName(),
        	                sw.getStudentName(),
        	                sw.getStudentSurname(),
        	                sw.getDegree().name()
        	            ))
        	            .toList()
        	    );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentWorkDTO> getById(
            @PathVariable("id") int id) throws Exception {

        StudentWork sw = swService.retrieveById(id);
        System.out.println(sw);
        return ResponseEntity.ok(
        		new StudentWorkDTO(
    	                sw.getIdStudWork(),
    	                sw.getName(),
    	                sw.getStudentName(),
    	                sw.getStudentSurname(),
    	                sw.getDegree().name()
    	            )
        );
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody StudentWorkDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        swService.create(dto.getName(), dto.getStudentName(), dto.getStudentSurname(), dto.getDegree());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") int id,
            @Valid @RequestBody StudentWorkDTO dto, BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        swService.updateById(id, dto.getName(), dto.getStudentName(), dto.getStudentSurname(), dto.getDegree());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) throws Exception {
        swService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/filter/{degree}")
    public ResponseEntity<ArrayList<StudentWorkDTO>> selectAllStudentWorkByDegree(
            @PathVariable("degree") String degree) throws Exception {

        ArrayList<StudentWork> studentWork =
                swService.selectAllStudentWorkByDegree(degree);

        ArrayList<StudentWorkDTO> response =
        	    new ArrayList<>(
        	    	studentWork.stream()
        	            .map(sw -> new StudentWorkDTO(
        	                sw.getIdStudWork(),
        	                sw.getName(),
        	                sw.getStudentName(),
        	                sw.getStudentSurname(),
        	                sw.getDegree().name()
        	            ))
        	            .toList()
        	    );
        
        return ResponseEntity.ok(response);
    }

}

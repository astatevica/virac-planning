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
import lv.venta.virac.dto.CourseDTO;
import lv.venta.virac.model.Course;
import lv.venta.virac.service.ICRUDCourseService;

@RestController
@RequestMapping("/api/admin/course")
@CrossOrigin(origins = "http://localhost:3000")
public class CRUDCourseController {
	
	private ICRUDCourseService courseService;
	
	public CRUDCourseController(ICRUDCourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping
    public ResponseEntity<ArrayList<CourseDTO>> getAll() throws Exception {

        ArrayList<Course> course = courseService.retrieveAll();

        ArrayList<CourseDTO> response =
        	    new ArrayList<>(
        	        course.stream()
        	            .map(co -> new CourseDTO(
        	            	co.getIdCourse(),
        	            	co.getName(),
        	            	co.getEctsCredits(),
        	            	co.getSemester(),
        	            	co.getFaculty()
        	            ))
        	            .toList()
        	    );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getById(
            @PathVariable("id") int id) throws Exception {

        Course co = courseService.retrieveById(id);
        System.out.println(co);
        return ResponseEntity.ok(
            new CourseDTO(
	            	co.getIdCourse(),
	            	co.getName(),
	            	co.getEctsCredits(),
	            	co.getSemester(),
	            	co.getFaculty()
	            )
        );
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody CourseDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        courseService.create(dto.getName(), dto.getEctsCredits(), dto.getSemester(), dto.getFaculty());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") int id,
            @Valid @RequestBody CourseDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        courseService.updateById(id, dto.getName(), dto.getEctsCredits(), dto.getSemester(), dto.getFaculty());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) throws Exception {
    	courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

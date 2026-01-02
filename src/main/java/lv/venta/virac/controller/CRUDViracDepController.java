package lv.venta.virac.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import lv.venta.virac.dto.DepartmentDTO;
import lv.venta.virac.model.ViracDepartment;
import lv.venta.virac.service.ICRUDViracDepService;

@RestController
@RequestMapping(value = "/api/department")
public class CRUDViracDepController {
	
	@Autowired
	private ICRUDViracDepService depService;
	
	public CRUDViracDepController(ICRUDViracDepService depService) {
	    this.depService = depService;
	}
	
	//Retrieve all
	@GetMapping(value = "")
	public ResponseEntity<ArrayList<DepartmentDTO>> getAllDepartments() throws Exception{
		
		ArrayList<ViracDepartment> departments = depService.retrieveAll();

		ArrayList<DepartmentDTO> response =
                (ArrayList<DepartmentDTO>) departments.stream()
		    .map(dep -> new DepartmentDTO(
		        dep.getIdDepartment(),
		        dep.getName()
		    ))
		    .toList();

        return ResponseEntity.ok(response);
	}
	
	//Retrieve by id
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable("id") int id) throws Exception {
    	
    	ViracDepartment dep = depService.retrieveById(id);

        DepartmentDTO response =
                new DepartmentDTO(
                    dep.getIdDepartment(),
                    dep.getName()
                );
    	
    	
        return ResponseEntity.ok(response);
    }
	
	//Create 
    @PostMapping
    public ResponseEntity<ViracDepartment> createDepartment(@Valid @RequestBody DepartmentDTO dto,
    		BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        depService.create(dto.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    //Update by id
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDepartment(
            @PathVariable("id") int id,
            @Valid @RequestBody DepartmentDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        depService.updateById(id, dto.getName());
        return ResponseEntity.ok().build();
    }
    
    
	//Delete by id	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable("id") int id) throws Exception {
        depService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

	

}

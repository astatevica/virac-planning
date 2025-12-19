package lv.venta.virac.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
import lv.venta.virac.model.ViracDepartment;
import lv.venta.virac.service.ICRUDViracDepService;

@RestController
@RequestMapping("/api/department")
public class CRUDViracDepController {
	
	@Autowired
	private ICRUDViracDepService depService;
	
	//Retrieve all
	@GetMapping
	public ArrayList<ViracDepartment> getAllDepartments() throws Exception{
		return depService.retrieveAll();
	}
	
	//Retrieve by id
    @GetMapping("/{id}")
    public ResponseEntity<ViracDepartment> getDepartmentById(@PathVariable int id) throws Exception {
        return ResponseEntity.ok(depService.retrieveById(id));
    }
	
	//Create 
    @PostMapping
    public ResponseEntity<ViracDepartment> createDepartment(@Valid @RequestBody ViracDepartment department,
    		BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        depService.create(department.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(department);
    }
    
    //Update by id
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDepartment(
            @PathVariable int id,
            @Valid @RequestBody ViracDepartment department,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        depService.updateById(id, department.getName());
        return ResponseEntity.ok().build();
    }
    
    
	//Delete by id	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable int id) throws Exception {
        depService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

	

}

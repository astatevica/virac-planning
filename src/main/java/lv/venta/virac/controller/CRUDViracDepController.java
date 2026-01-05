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
import lv.venta.virac.dto.DepartmentDTO;
import lv.venta.virac.dto.DepartmentRequestDTO;
import lv.venta.virac.model.ViracDepartment;
import lv.venta.virac.service.ICRUDViracDepService;

@RestController
@RequestMapping("/api/department")
@CrossOrigin(origins = "http://localhost:3000")
public class CRUDViracDepController {

    private final ICRUDViracDepService depService;

    public CRUDViracDepController(ICRUDViracDepService depService) {
        this.depService = depService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<DepartmentDTO>> getAllDepartments() throws Exception {

        ArrayList<ViracDepartment> departments = depService.retrieveAll();

        ArrayList<DepartmentDTO> response =
        	    new ArrayList<>(
        	        departments.stream()
        	            .map(dep -> new DepartmentDTO(
        	                dep.getIdDepartment(),
        	                dep.getName()
        	            ))
        	            .toList()
        	    );

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getById(
            @PathVariable("id") int id) throws Exception {

        ViracDepartment dep = depService.retrieveById(id);
        return ResponseEntity.ok(
            new DepartmentDTO(dep.getIdDepartment(), dep.getName())
        );
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody DepartmentRequestDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        depService.create(dto.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") int id,
            @Valid @RequestBody DepartmentRequestDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        depService.updateById(id, dto.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) throws Exception {
        depService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

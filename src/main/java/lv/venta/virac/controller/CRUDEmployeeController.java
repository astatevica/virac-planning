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
import lv.venta.virac.dto.EmployeeDTO;
import lv.venta.virac.model.Employee;
import lv.venta.virac.service.ICRUDEmployeeService;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "http://localhost:3000")
public class CRUDEmployeeController {
	
	private ICRUDEmployeeService emplService;

    public CRUDEmployeeController(ICRUDEmployeeService emplService) {
		this.emplService = emplService;
	}

    @GetMapping
    public ResponseEntity<ArrayList<EmployeeDTO>> getAllDEmployees() throws Exception {

        ArrayList<Employee> employee = emplService.retrieveAll();

        ArrayList<EmployeeDTO> response =
        	    new ArrayList<>(
        	        employee.stream()
        	            .map(emp -> new EmployeeDTO(
        	                emp.getIdEmployee(),
        	                emp.getName(),
        	                emp.getSurname(),
        	                emp.getViracDepartment(),
        	                emp.getPosition()
        	            ))
        	            .toList()
        	    );

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getById(
            @PathVariable("id") int id) throws Exception {

        Employee emp = emplService.retrieveById(id);
        return ResponseEntity.ok(
            new EmployeeDTO(emp.getIdEmployee(), emp.getName(),emp.getSurname(),
            		emp.getViracDepartment(),emp.getPosition())
        );
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody EmployeeDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        emplService.create(dto.getName(),dto.getSurname(),dto.getDepartment(),dto.getPosition());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") int id,
            @Valid @RequestBody EmployeeDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        emplService.updateById(id, dto.getName(), dto.getSurname(), dto.getDepartment(), dto.getPosition());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) throws Exception {
        emplService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    //FILTER BY DEPARTMENT
    @GetMapping("/filter/{departmentId}")
    public ResponseEntity<ArrayList<EmployeeDTO>> getEmployeesByDepartment(
            @PathVariable("departmentId") int departmentId) throws Exception {

        ArrayList<Employee> employees =
                emplService.selectAllEmployeesByDepartment(departmentId);

        ArrayList<EmployeeDTO> response = new ArrayList<>(
                employees.stream()
                    .map(emp -> new EmployeeDTO(
                    	emp.getIdEmployee(),
                    	emp.getName(),
                    	emp.getSurname(),
                    	emp.getViracDepartment(),
                    	emp.getPosition()
                    ))
                    .toList());
        
       

        return ResponseEntity.ok(response);
    }

}

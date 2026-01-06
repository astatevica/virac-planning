package lv.venta.virac.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lv.venta.virac.dto.ProjectManagementDTO;
import lv.venta.virac.model.ProjectManagement;
import lv.venta.virac.service.ICRUDProjManagService;

@RestController
@RequestMapping("/api/project-management")
@CrossOrigin(origins = "http://localhost:3000")
public class CRUDProjManagController {
	
	 private final ICRUDProjManagService projService;

	    public CRUDProjManagController(ICRUDProjManagService projService) {
	        this.projService = projService;
	    }

	    /* ===================== GET ALL ===================== */

	    @GetMapping
	    public ResponseEntity<ArrayList<ProjectManagementDTO>> getAll()
	            throws Exception {

	        ArrayList<ProjectManagement> list = projService.retrieveAll();

	        ArrayList<ProjectManagementDTO> response =
	            new ArrayList<>(list.stream()
	                .map(pm -> new ProjectManagementDTO(
	                        pm.getIdProjectManag(),
	                        pm.getEmployee(),
	                        pm.getStartDate(),
	                        pm.getEndDate()
	                ))
	                .toList());

	        return ResponseEntity.ok(response);
	    }

	    /* ===================== GET BY ID ===================== */

	    @GetMapping("/{id}")
	    public ResponseEntity<ProjectManagementDTO> getById(
	            @PathVariable("id") int id) throws Exception {

	        ProjectManagement pm = projService.retrieveById(id);

	        return ResponseEntity.ok(
	            new ProjectManagementDTO(
	                pm.getIdProjectManag(),
	                pm.getEmployee(),
	                pm.getStartDate(),
	                pm.getEndDate()
	            )
	        );
	    }

	    /* ===================== CREATE ===================== */

	    @PostMapping
	    public ResponseEntity<Void> create(
	            @RequestBody ProjectManagementDTO dto) throws Exception {

	    	System.out.println("MANAGEMENT ID: " + dto.getIdProjectManag());
	        System.out.println("EMPLOYEE: " + dto.getEmployee().getIdEmployee());
	        System.out.println("START DATE: " + dto.getStartDate());
	        System.out.println("END DATE: " + dto.getEndDate());

//	        Employee emp = new Employee();
//	        emp.setIdEmployee(dto.getEmployeeId());

	        projService.create(
	            //emp,
	        	dto.getEmployee(),
	            dto.getStartDate(),
	            dto.getEndDate()
	        );

	        return ResponseEntity.status(HttpStatus.CREATED).build();
	    }

	    /* ===================== UPDATE ===================== */

	    @PutMapping("/{id}")
	    public ResponseEntity<Void> update(
	            @PathVariable("id") int id,
	            @RequestBody ProjectManagementDTO dto) throws Exception {

//	        Employee emp = new Employee();
//	        emp.setIdEmployee(dto.getEmployeeId());

	        projService.updateById(
	            id,
	            //emp,
	            dto.getEmployee(),
	            dto.getStartDate(),
	            dto.getEndDate()
	        );

	        return ResponseEntity.ok().build();
	    }

	    /* ===================== DELETE ===================== */

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> delete(
	            @PathVariable("id") int id) throws Exception {

	        projService.deleteById(id);
	        return ResponseEntity.noContent().build();
	    }

	    /* ===================== FILTERS ===================== */

	    @GetMapping("/employee/{idEmployee}")
	    public ResponseEntity<ArrayList<ProjectManagementDTO>>
	    filterByEmployee(@PathVariable("idEmployee") int employeeId) throws Exception {

	        ArrayList<ProjectManagement> list =
	            projService.selectAllProjectManagemetByEmployee(employeeId);

	        return ResponseEntity.ok(mapToDTO(list));
	    }

	    @GetMapping("/start-date/{startDate}")
	    public ResponseEntity<ArrayList<ProjectManagementDTO>>
	    filterByStartDate(@PathVariable("startDate") LocalDate date) throws Exception {

	        return ResponseEntity.ok(
	            mapToDTO(projService.selectAllProjectManagemetByStartDate(date))
	        );
	    }

	    @GetMapping("/end-date/{endDate}")
	    public ResponseEntity<ArrayList<ProjectManagementDTO>>
	    filterByEndDate(@PathVariable("endDate") LocalDate date) throws Exception {

	        return ResponseEntity.ok(
	            mapToDTO(projService.selectAllProjectManagemetByEndDate(date))
	        );
	    }

	    /* ===================== HELPER ===================== */

	    private ArrayList<ProjectManagementDTO> mapToDTO(
	            ArrayList<ProjectManagement> list) {

	        return new ArrayList<>(list.stream()
	            .map(pm -> new ProjectManagementDTO(
	                    pm.getIdProjectManag(),
	                    pm.getEmployee(),
	                    pm.getStartDate(),
	                    pm.getEndDate()
	            ))
	            .toList());
	    }

}

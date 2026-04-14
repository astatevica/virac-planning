package lv.venta.virac.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lv.venta.virac.dto.ProjectDTO;
import lv.venta.virac.model.Project;
import lv.venta.virac.service.ICRUDProjectService;

@RestController
@RequestMapping("/api/admin/project")
@CrossOrigin(origins = "http://localhost:3000")
public class CRUDProjectController {
	
	private ICRUDProjectService projService;
	
	public CRUDProjectController(ICRUDProjectService projectService) {
		this.projService = projectService;
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArrayList<ProjectDTO>> getAll()
            throws Exception {

        ArrayList<Project> list = projService.retrieveAll();

        ArrayList<ProjectDTO> response =
	            new ArrayList<>(list.stream()
	                .map(pr -> new ProjectDTO(
	                		pr.getIdProject(),
	                		pr.getName(),
	                		pr.getNumber(),
	                        pr.getProjectManagement().getIdProjectManag(),
	                        pr.getStartDate(),
	                        pr.getEndDate(),
	                        pr.getAcronym()
	                ))
	                .toList());

	    return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectDTO> getById(
            @PathVariable("id") int id) throws Exception {

        Project pr = projService.retrieveById(id);

        return ResponseEntity.ok(
        		new ProjectDTO(
        				pr.getIdProject(),
                		pr.getName(),
                		pr.getNumber(),
                        pr.getProjectManagement().getIdProjectManag(),
                        pr.getStartDate(),
                        pr.getEndDate(),
                        pr.getAcronym()
	            )
	        );
    }
    
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(
            @RequestBody ProjectDTO dto) throws Exception {

        projService.create(
        	dto.getName(),
        	dto.getNumber(),
        	dto.getManagementId(),
        	dto.getStartDate(),
        	dto.getEndDate(),
        	dto.getAcronym()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> update(
            @PathVariable("id") int id,
            @RequestBody ProjectDTO dto) throws Exception {

        projService.updateById(
            id,
            dto.getName(),
        	dto.getNumber(),
        	dto.getManagementId(),
        	dto.getStartDate(),
        	dto.getEndDate(),
        	dto.getAcronym()
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable("id") int id) throws Exception {

        projService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/number/{number}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArrayList<ProjectDTO>>
    filterByNumber(@PathVariable("number") int number) throws Exception {

        ArrayList<Project> list =
            projService.selectAllProjectsByNumber(number);

        return ResponseEntity.ok(mapToDTO(list));
    }

    @GetMapping("/start-date/{startDate}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArrayList<ProjectDTO>>
    filterByStartDate(@PathVariable("startDate") LocalDate date) throws Exception {

        return ResponseEntity.ok(
            mapToDTO(projService.selectAllProjectsByStartDate(date))
        );
    }

    @GetMapping("/end-date/{endDate}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArrayList<ProjectDTO>>
    filterByEndDate(@PathVariable("endDate") LocalDate date) throws Exception {

        return ResponseEntity.ok(
            mapToDTO(projService.selectAllProjectsByEndDate(date))
        );
    }
    
  //Helper
    private ArrayList<ProjectDTO> mapToDTO(
            ArrayList<Project> list) {

        return new ArrayList<>(list.stream()
            .map(pr -> new ProjectDTO(
            		pr.getIdProject(),
            		pr.getName(),
            		pr.getNumber(),
                    pr.getProjectManagement().getIdProjectManag(),
                    pr.getStartDate(),
                    pr.getEndDate(),
                    pr.getAcronym()
            ))
            .toList());
    }

}

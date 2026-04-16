package lv.venta.virac.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import lv.venta.virac.dto.YearDTO;
import lv.venta.virac.model.Year;
import lv.venta.virac.service.ICRUDYearService;

@RestController
@RequestMapping("/api/year")
@CrossOrigin(origins = "http://localhost:3000")
public class CRUDYearController {

	private ICRUDYearService yearService;
	
	public CRUDYearController(ICRUDYearService yearService) {
		this.yearService = yearService;
	}

	@GetMapping("/all")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'USER_DEPART')")
    public ResponseEntity<ArrayList<YearDTO>> getAll() throws Exception {

        ArrayList<Year> year = yearService.retrieveAll();

        ArrayList<YearDTO> response =
        	    new ArrayList<>(
        	    		year.stream()
        	            .map(y -> new YearDTO(
        	               y.getIdYear(),
        	               y.getYearNumber()
        	            ))
        	            .toList()
        	    );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<YearDTO> getById(
            @PathVariable("id") int id) throws Exception {

        Year y = yearService.retrieveById(id);
        System.out.println(y);
        return ResponseEntity.ok(
        		new YearDTO(
     	               y.getIdYear(),
     	               y.getYearNumber()
     	            ));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> create(
            @Valid @RequestBody YearDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        yearService.create(dto.getYearNumber());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") int id,
            @Valid @RequestBody YearDTO dto,BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        yearService.updateById(id, dto.getYearNumber());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) throws Exception {
    	yearService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

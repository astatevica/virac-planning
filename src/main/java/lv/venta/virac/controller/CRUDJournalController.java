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
import lv.venta.virac.dto.JournalDTO;
import lv.venta.virac.model.Journal;
import lv.venta.virac.service.ICRUDJournalService;

@RestController
@RequestMapping("/api/journal")
@CrossOrigin(origins = "http://localhost:3000")
public class CRUDJournalController {
	
	private ICRUDJournalService jourService;
	
	public CRUDJournalController(ICRUDJournalService jourService) {
		this.jourService = jourService;
	}

	@GetMapping
    public ResponseEntity<ArrayList<JournalDTO>> getAllJournals() throws Exception {

        ArrayList<Journal> journals = jourService.retrieveAll();

        ArrayList<JournalDTO> response =
        	    new ArrayList<>(
        	    		journals.stream()
        	            .map(jou -> new JournalDTO(
        	            	jou.getIdJournal(),
        	                jou.getName()
        	            ))
        	            .toList()
        	    );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<JournalDTO> getById(
            @PathVariable("id") int id) throws Exception {

        Journal jou = jourService.retrieveById(id);
        System.out.println(jou);
        return ResponseEntity.ok(
            new JournalDTO(jou.getIdJournal(),
	                jou.getName()
        ));
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody JournalDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        jourService.create(dto.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") int id,
            @Valid @RequestBody JournalDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        jourService.updateById(id, dto.getName());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) throws Exception {
        jourService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

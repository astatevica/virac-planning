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
import lv.venta.virac.dto.ScientificArticlesDTO;
import lv.venta.virac.model.ScientificArticles;
import lv.venta.virac.service.ICRUDScientificArticlesService;

@RestController
@RequestMapping("/api/admin/scientific-articles")
@CrossOrigin(origins = "http://localhost:3000")
public class CRUDScientificArticlesController {
	
	private ICRUDScientificArticlesService artService;
	
	public CRUDScientificArticlesController(ICRUDScientificArticlesService artService) {
		this.artService = artService;
	}
	
	@GetMapping
    public ResponseEntity<ArrayList<ScientificArticlesDTO>> getAllArticles() throws Exception {

        ArrayList<ScientificArticles> articles = artService.retrieveAll();

        ArrayList<ScientificArticlesDTO> response =
        	    new ArrayList<>(
        	        articles.stream()
        	            .map(art -> new ScientificArticlesDTO(
        	               art.getIdArticle(),
        	               art.getName(),
        	               art.getCoAuthors(),
        	               art.getJournal().getIdJournal()
        	            ))
        	            .toList()
        	    );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ScientificArticlesDTO> getById(
            @PathVariable("id") int id) throws Exception {

        ScientificArticles art = artService.retrieveById(id);
        System.out.println(art);
        return ResponseEntity.ok(
            new ScientificArticlesDTO(art.getIdArticle(),art.getName(),art.getCoAuthors(), art.getJournal().getIdJournal())
        );
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody ScientificArticlesDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        artService.create(dto.getName(), dto.getCoAuthors(), dto.getIdJournal());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") int id,
            @Valid @RequestBody ScientificArticlesDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        artService.updateById(id, dto.getName(), dto.getCoAuthors(), dto.getIdJournal());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) throws Exception {
    	artService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/filter/{coAuthors}")
    public ResponseEntity<ArrayList<ScientificArticlesDTO>> selectAllScientificArticlesByCoauthor(
            @PathVariable("coAuthors") String coAuthors) throws Exception {

        ArrayList<ScientificArticles> articles =
                artService.selectAllScientificArticlesByCoauthor(coAuthors);

        ArrayList<ScientificArticlesDTO> response =
        	    new ArrayList<>(
        	        articles.stream()
        	            .map(art -> new ScientificArticlesDTO(
        	               art.getIdArticle(),
        	               art.getName(),
        	               art.getCoAuthors(),
        	               art.getJournal().getIdJournal()
        	            ))
        	            .toList()
        	    );
        
        return ResponseEntity.ok(response);
    }

}

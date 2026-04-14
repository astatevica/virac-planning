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
import lv.venta.virac.dto.ArticlePlanDTO;
import lv.venta.virac.model.ArticlePlan;
import lv.venta.virac.service.ICRUDArticlePlanService;

@RestController
@RequestMapping("/api/admin/article-plan")
@CrossOrigin(origins = "http://localhost:3000")
public class CRUDArticlePlanController {

	private ICRUDArticlePlanService artPlanService;
	
	public CRUDArticlePlanController(ICRUDArticlePlanService artPlanService) {
		this.artPlanService = artPlanService;
	}
	
	@GetMapping("/all")
    public ResponseEntity<ArrayList<ArticlePlanDTO>> getAll() throws Exception {

        ArrayList<ArticlePlan> artPlans = artPlanService.retrieveAll();

        ArrayList<ArticlePlanDTO> response =
        	    new ArrayList<>(
        	        artPlans.stream()
        	            .map(art -> new ArticlePlanDTO(
        	               art.getIdArticlePlan(),
        	               art.getPlan().getIdPlan(),
        	               art.getScientificArticles().getIdArticle(),
        	               art.getArticleComments(),
        	               art.getPublicationLink()
        	            ))
        	            .toList()
        	    );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ArticlePlanDTO> getById(
            @PathVariable("id") int id) throws Exception {

        ArticlePlan art = artPlanService.retrieveById(id);
        System.out.println(art);
        return ResponseEntity.ok(
            new ArticlePlanDTO(
            	art.getIdArticlePlan(),
            	art.getPlan().getIdPlan(),
 	            art.getScientificArticles().getIdArticle(),
 	            art.getArticleComments(),
 	            art.getPublicationLink())
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Void> create(
            @Valid @RequestBody ArticlePlanDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        artPlanService.create(dto.getIdPlan(),dto.getIdArticlePlan(),dto.getArticleComments(), dto.getPublicationLink());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") int id,
            @Valid @RequestBody ArticlePlanDTO dto,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        artPlanService.updateById(id, dto.getIdPlan(),dto.getIdArticlePlan(),dto.getArticleComments(), dto.getPublicationLink());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) throws Exception {
    	artPlanService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/filter/{idPlan}")
    public ResponseEntity<ArrayList<ArticlePlanDTO>> selectAllArticlePlanByPlan(
            @PathVariable("idPlan") int idPlan) throws Exception {

        ArrayList<ArticlePlan> artPlan =
                artPlanService.selectAllArticlePlanByPlan(idPlan);

        ArrayList<ArticlePlanDTO> response =
        	    new ArrayList<>(
        	        artPlan.stream()
        	            .map(art -> new ArticlePlanDTO(
        	               art.getIdArticlePlan(),
        	               art.getPlan().getIdPlan(),
        	               art.getScientificArticles().getIdArticle(),
        	               art.getArticleComments(),
        	               art.getPublicationLink()
        	            ))
        	            .toList()
        	    );
        return ResponseEntity.ok(response);
    }


}

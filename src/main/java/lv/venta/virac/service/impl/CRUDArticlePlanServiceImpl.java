package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.dto.ArticlePlanCommentsDTO;
import lv.venta.virac.dto.ArticlePlanReponseDTO;
import lv.venta.virac.dto.ScientificArticlesCommentsDTO;
import lv.venta.virac.model.ArticlePlan;
import lv.venta.virac.model.Journal;
import lv.venta.virac.model.Plan;
import lv.venta.virac.model.ScientificArticles;
import lv.venta.virac.repo.IArticlePlanRepo;
import lv.venta.virac.repo.IJournalRepo;
import lv.venta.virac.repo.IPlanRepo;
import lv.venta.virac.repo.IScientificArticlesRepo;
import lv.venta.virac.service.ICRUDArticlePlanService;

@Service
public class CRUDArticlePlanServiceImpl implements ICRUDArticlePlanService{

	private final IArticlePlanRepo artPlanRepo;
	
	private final IPlanRepo planRepo;

	private final IScientificArticlesRepo artRepo;

	private final IJournalRepo journalRepo;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public CRUDArticlePlanServiceImpl(IArticlePlanRepo artPlanRepo, IPlanRepo planRepo, 
			IScientificArticlesRepo artRepo, IJournalRepo journalRepo) {
		this.artPlanRepo = artPlanRepo;
		this.planRepo = planRepo;
		this.artRepo = artRepo;
		this.journalRepo = journalRepo;
	}
	
	@Override
	public ArrayList<ArticlePlan> retrieveAll() throws Exception {
		Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedArticlePlanFilter");
        filter.setParameter("isDeleted", false);
        ArrayList<ArticlePlan> articlePlans = (ArrayList<ArticlePlan>) artPlanRepo.findAll();
        if (articlePlans.isEmpty()) {throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There is no article-plans");}
        session.disableFilter("deletedArticlePlanFilter");
        return articlePlans;
	}

	@Override
	public ArticlePlan retrieveById(int id) {

	    if (id < 1) {
	        throw new ResponseStatusException(
	                HttpStatus.BAD_REQUEST,
	                "Invalid ID"
	        );
	    }

	    return artPlanRepo.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND,
	                    "ArticlePlan not found with id: " + id
	            ));
	}

	@Override
	public void deleteById(int id) throws Exception {
		
		ArticlePlan artPlan = artPlanRepo.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "ArticlePlan not found with id: " + id
        ));
    	artPlan.setDeleted(true); // SOFT DELETE
    	artPlanRepo.save(artPlan);  // SAVE, NOT DELETE
	}

	@Override
	public void create(int idPlan, int idScientificArticles, String articleComments, String publicationLink)
			throws Exception {
		ArrayList<ArticlePlan> artPlan = (ArrayList<ArticlePlan>) artPlanRepo.findAll();
        
        if(idPlan == 0 || idScientificArticles == 0){
        	throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The input parameters are incorrect"
            );
		}
        
        Plan plan = planRepo.findById(idPlan).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Plan not found with id " + idPlan 
        ));
        
        ScientificArticles article = artRepo.findById(idScientificArticles).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Article not found with id " + idScientificArticles 
        ));
     
        
        for (ArticlePlan art : artPlan) {
            if (art.getPlan().getIdPlan() == idPlan && art.getScientificArticles().getIdArticle() == idScientificArticles && art.isDeleted( )) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Scientific article-plan with paln id: " + art.getPlan().getIdPlan() + " and artticle id: " 
                                + art.getScientificArticles().getIdArticle() + " already exists"
                );
            }
        }

        ArticlePlan articlePlan = new ArticlePlan(plan, article, articleComments, publicationLink);
        artPlanRepo.save(articlePlan);
	}

	@Override
	public void updateById(int id, int idPlan, int idScientificArticles, String articleComments, String publicationLink)
			throws Exception {
		ArticlePlan articlePlan = retrieveById(id);
    	if (articlePlan == null) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Article-plan with (id:" + id + ") does not exist"
        );
    	
    	
    	Plan plan = planRepo.findById(idPlan).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Plan not found with idPlan " + idPlan 
        ));
        
        ScientificArticles article = artRepo.findById(idScientificArticles).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Article not found with idArticle " + idScientificArticles
        ));
    	
        articlePlan.setPlan(plan);
        articlePlan.setScientificArticles(article);
        articlePlan.setArticleComments(articleComments);
        articlePlan.setPublicationLink(publicationLink);
        artPlanRepo.save(articlePlan);
		
	}

	@Override
	public ArrayList<ArticlePlan> selectAllArticlePlanByPlan(int idPlan) throws Exception {
		ArrayList<ArticlePlan> result = artPlanRepo.findByPlan_IdPlan(idPlan);
		if(result.isEmpty()) {
			throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Article-plan with plan ID: " + idPlan + " does not exist"
            );
		}
		
		return result;
	}

	@Override
	public void createAutocompleteArticle(int idPlan, int idArticle,  ArticlePlanCommentsDTO dto, int employeeId) throws Exception {
		ArticlePlan ap = artPlanRepo.findByPlan_IdPlanAndScientificArticles_IdArticle(idPlan,idArticle);
        
        if(idPlan == 0 || idArticle == 0){
        	throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The input parameters are incorrect"
            );
		}
        
        Plan plan = planRepo.findById(idPlan).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Plan not found"
        ));
        
        if(employeeId != plan.getEmployee().getIdEmployee()) {
        	throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "This user: "+ plan.getEmployee().getName() + " " + plan.getEmployee().getSurname() +" can't edit current plan"
            );
        	}
        
        ScientificArticles article = artRepo.findById(idArticle).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Article not found"
        ));
        
        if(ap != null) {
            if(!ap.isDeleted()){
            	throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Article already attached to this plan"
                );
            }
            ap.setDeleted(false);
            ap.setArticleComments(dto.getArticleComments());
            ap.setPublicationLink(dto.getPublicationLink());
            artPlanRepo.save(ap);
            return;
        }
        ArticlePlan articlePlan = new ArticlePlan(plan, article, dto.getArticleComments(), dto.getPublicationLink());
        artPlanRepo.save(articlePlan);
	}

	@Override
	public ArticlePlanReponseDTO createArticleAndAttachToPlan(int idPlan, ScientificArticlesCommentsDTO articleDTO,
			int employeeId) throws Exception {
		//variables for easier use
		String name = articleDTO.getName();
		String coAuthors = articleDTO.getCoAuthors();
		int idJournal = articleDTO.getIdJournal();
		String comments = articleDTO.getArticleComments();
		String link = articleDTO.getPublicationLink();
		
		//reads already made articles
		ArrayList<ScientificArticles> articles = (ArrayList<ScientificArticles>) artRepo.findAll();
        
		//verifies that article and idPlan input parameters are not empty
        if(name == null || coAuthors == null || idJournal == 0 || idPlan == 0){
			throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The input parameters are incorrect"
            );
		}
        
        //verifies that article already is not made
        for (ScientificArticles art : articles) {
            if (art.getName().equals(name) && art.getCoAuthors().equals(coAuthors) && art.getJournal().getIdJournal() == idJournal && art.isDeleted( )) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Article: " + art.getName() + " already exists"
                );
            }
        }
        
        //verifies that plan is correct
        Plan plan = planRepo.findById(idPlan).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Plan not found"
        ));
        
        //verifies that plan is connected to right user
        if(employeeId != plan.getEmployee().getIdEmployee()) {
        	throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "This user: "+ plan.getEmployee().getName() + " " + plan.getEmployee().getSurname() +" can't edit current plan"
            );
        }
	    
        //Makes new Article after veryfing
		ScientificArticles a = new ScientificArticles();
	    a.setName(name);
	    a.setCoAuthors(coAuthors);
	    Journal journal = journalRepo.findById(idJournal).orElseThrow(() -> new RuntimeException("Journal not found with id: " + idJournal));
	    a.setJournal(journal);
	    ScientificArticles newArticle = artRepo.save(a);
	    
	    //Creates new Plan-Article relation
	    ArticlePlan ap = new ArticlePlan();
	    Plan planArticle = planRepo.findById(idPlan).orElseThrow(() -> new RuntimeException("Journal not found with id: " + idJournal));
	    ap.setPlan(planArticle);
	    ap.setScientificArticles(newArticle);
	    ap.setDeleted(false);
	    ap.setArticleComments(comments);
	    ap.setPublicationLink(link);
	    artPlanRepo.save(ap);
	    
	    //Returns ArticlePlan dto for frontend
	    ArticlePlanReponseDTO dto = new ArticlePlanReponseDTO();
	    dto.setName(name);
	    dto.setIdPlan(idPlan);
	    dto.setIdJournal(idJournal);
	    dto.setIdArticle(idJournal);
	    dto.setCoAuthors(coAuthors);
	    dto.setArticleComments(comments);
	    dto.setPublicationLink(link);

	    return dto;
	}

	@Override
	public void deleteByArticleIdAndPlanId(int idPlan, int idArticle, int employeeId) throws Exception {
		ArticlePlan articlePlan = artPlanRepo.findByPlan_IdPlanAndScientificArticles_IdArticle(idPlan,idArticle);
    	if (articlePlan == null) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Article-Plan with Plan id:"+ idPlan +" and Article id: "+idArticle+" does not exist"
        );
    	
    	if(employeeId != articlePlan.getCreatedBy()) {
    		
        	throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "This user: "+ articlePlan.getPlan().getEmployee().getName() + " " 
                        	+ articlePlan.getPlan().getEmployee().getSurname() +" can't edit current plan"
            );
        }
    	articlePlan.setDeleted(true); // SOFT DELETE
    	artPlanRepo.save(articlePlan);  // SAVE, NOT DELETE
	}

	@Override
	public void updateByArticleIdAndPlanId(int idPlan, int idArticle, String articleComments, String publicationLink,
			int employeeId) throws Exception {
		ArticlePlan articlePlan = artPlanRepo.findByPlan_IdPlanAndScientificArticles_IdArticle(idPlan,idArticle);
    	if (articlePlan == null) 
    		throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Article-Plan with Plan id:"+ idPlan +" and Article id: "+idArticle+" does not exist"
            );
    	if(employeeId != articlePlan.getCreatedBy()) {
        	throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "This user: "+ articlePlan.getPlan().getEmployee().getName() + " " 
                        	+ articlePlan.getPlan().getEmployee().getSurname() +" can't edit current plan"
            );
        }
        articlePlan.setArticleComments(articleComments);
        articlePlan.setPublicationLink(publicationLink);
        artPlanRepo.save(articlePlan);
		
	}

}

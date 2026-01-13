package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.model.ArticlePlan;
import lv.venta.virac.model.Plan;
import lv.venta.virac.model.ScientificArticles;
import lv.venta.virac.repo.IArticlePlanRepo;
import lv.venta.virac.repo.IPlanRepo;
import lv.venta.virac.repo.IScientificArticlesRepo;
import lv.venta.virac.service.ICRUDArticlePlanService;

@Service
public class CRUDArticlePlanServiceImpl implements ICRUDArticlePlanService{

	@Autowired
	private IArticlePlanRepo artPlanRepo;
	
	@Autowired
	private IPlanRepo planRepo;
	
	@Autowired
	private IScientificArticlesRepo artRepo;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public ArrayList<ArticlePlan> retrieveAll() throws Exception {
		Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedArticlePlanFilter");
        filter.setParameter("isDeleted", false);
        ArrayList<ArticlePlan> articlePlans = (ArrayList<ArticlePlan>) artPlanRepo.findAll();
        if (articlePlans.isEmpty()) throw new Exception("There is no article-plans");
        session.disableFilter("deletedArticlePlanFilter");
        return articlePlans;
	}

	@Override
	public ArticlePlan retrieveById(int id) throws Exception {
		if (id < 1) throw new Exception("Invalid ID");
        ArticlePlan foundArtPlan = artPlanRepo.findById(id).get();
        if (foundArtPlan == null) throw new Exception("Article-Plan with the id: (" + id + ") does not exist!");
        
        return foundArtPlan;
	}

	@Override
	public void deleteById(int id) throws Exception {
		ArticlePlan artPlan = artPlanRepo.findById(id).get();
    	if (artPlan == null) throw new Exception("Article with id:"+ id +" does not exist");
    	artPlan.setDeleted(true); // SOFT DELETE
    	artPlanRepo.save(artPlan);  // SAVE, NOT DELETE
	}

	@Override
	public void create(int idPlan, int idScientificArticles, String articleComments, String publicationLink)
			throws Exception {
		ArrayList<ArticlePlan> artPlan = (ArrayList<ArticlePlan>) artPlanRepo.findAll();
        
        if(idPlan == 0 || idScientificArticles == 0){
			throw new Exception("The input parameters are incorrect");
		}
        
        Plan plan = planRepo.findById(idPlan).get();
        if(plan == null) {
        	throw new Exception("Plan not found");
        }
        
        ScientificArticles article = artRepo.findById(idScientificArticles).get();
        if(article == null) {
        	throw new Exception("Article not found");
        }
        
        for (ArticlePlan art : artPlan) {
            if (art.getPlan().getIdPlan() == idPlan & art.getScientificArticles().getIdArticle() == idScientificArticles) {
                throw new Exception("Scientific article-plan with paln id: " + art.getPlan().getIdPlan() + " and artticle id: " 
            + art.getScientificArticles().getIdArticle() + " already exists");
            }
        }

        ArticlePlan articlePlan = new ArticlePlan(plan, article, articleComments, publicationLink);
        artPlanRepo.save(articlePlan);
	}

	@Override
	public void updateById(int id, int idPlan, int idScientificArticles, String articleComments, String publicationLink)
			throws Exception {
		ArticlePlan articlePlan = retrieveById(id);
    	if (articlePlan == null) throw new 
    		Exception("Article-plan with (id:" + id + ") does not exist");    	
    	
    	Plan plan = planRepo.findById(idPlan).get();
        if(plan == null) {
        	throw new Exception("Plan not found");
        }
        
        ScientificArticles article = artRepo.findById(idScientificArticles).get();
        if(article == null) {
        	throw new Exception("Article not found");
        }
    	
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
			throw new Exception("Article-plan with plan ID: " + idPlan + " does not exist");
		}
		
		return result;
	}

}

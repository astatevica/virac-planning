package lv.venta.virac.repo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import lv.venta.virac.model.ArticlePlan;
import lv.venta.virac.model.Plan;
import lv.venta.virac.model.ScientificArticles;
import lv.venta.virac.model.enums.PlanStatus;
import lv.venta.virac.repo.IArticlePlanRepo;
import lv.venta.virac.repo.IPlanRepo;
import lv.venta.virac.repo.IScientificArticlesRepo;

@DataJpaTest
@ActiveProfiles("test")
public class IArticlePlanRepoTest {
	
	@Autowired
    private IArticlePlanRepo articlePlanRepo;
	
	@Autowired
    private IPlanRepo planRepo;
	
	@Autowired
    private IScientificArticlesRepo articleRepo;
	
	private Plan plan;
	private ScientificArticles article;
	private ArticlePlan ap;
	
	@BeforeEach
	void setUp() {
		plan = new Plan();
		article = new ScientificArticles();
		ap = new ArticlePlan();
		
		plan.setNumOfStudWork(1);
		plan.setPlanStatus(PlanStatus.plan_open);
		plan = planRepo.save(plan);
		article.setName("Test name");
		article.setCoAuthors("Test coAuthors");
	    article = articleRepo.save(article);
	}

    @Test
    void testFindByPlanIdPlan() {
    	
        ap.setPlan(plan);
        ap.setScientificArticles(article);

        articlePlanRepo.save(ap);
        ArrayList<ArticlePlan> result = articlePlanRepo.findByPlan_IdPlan(plan.getIdPlan());
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindByPlanIdPlanAndScientificArticlesIdArticle() {
        ap.setPlan(plan);
        ap.setScientificArticles(article);

        articlePlanRepo.save(ap);

        ArticlePlan result =articlePlanRepo.findByPlan_IdPlanAndScientificArticles_IdArticle(
                plan.getIdPlan(),
                article.getIdArticle()
        );

        assertNotNull(result);
    }

    @Test
    void testFindByPlanIdPlanAndDeletedFalse() {
        ap.setPlan(plan);
        ap.setScientificArticles(article);
        ap.setDeleted(false);

        articlePlanRepo.save(ap);

        ArrayList<ArticlePlan> result = articlePlanRepo.findByPlan_IdPlanAndDeletedFalse(plan.getIdPlan());

        assertEquals(1, result.size());
    }

}

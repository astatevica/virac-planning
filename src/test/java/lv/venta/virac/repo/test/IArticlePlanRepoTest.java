package lv.venta.virac.repo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lv.venta.virac.model.ArticlePlan;
import lv.venta.virac.model.Plan;
import lv.venta.virac.model.ScientificArticles;
import lv.venta.virac.repo.IArticlePlanRepo;

@DataJpaTest(properties = {
		"spring.jpa.hibernate.ddl-auto=create", 
		"spring.datasource.url=jdbc:mysql:"
})//TODO: Saprast kāpēc šie testi neaiziet
public class IArticlePlanRepoTest {
	
	@Autowired
    private IArticlePlanRepo articlePlanRepo;
	
	private static Plan plan;
	private static ScientificArticles article;
	private static ArticlePlan ap;
	
	@BeforeAll
	static void setUp() {
		plan = new Plan();
		article = new ScientificArticles();
		ap = new ArticlePlan();
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

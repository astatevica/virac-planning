package lv.venta.virac.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.ArticlePlan;

public interface IArticlePlanRepo extends CrudRepository<ArticlePlan, Integer>{
	
	public abstract ArrayList<ArticlePlan> findByPlan_IdPlan(int idPlan);
	
	public abstract ArticlePlan findByPlan_IdPlanAndScientificArticles_IdArticle(int idPlan, int idArticle);

}

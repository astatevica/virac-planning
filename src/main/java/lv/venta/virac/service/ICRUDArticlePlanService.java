package lv.venta.virac.service;

import java.util.ArrayList;

import lv.venta.virac.model.ArticlePlan;

public interface ICRUDArticlePlanService extends ICRUDBase<ArticlePlan>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE

	//C - create 
	public abstract void create(int idPlan, int idScientificArticles, String articleComments, String publicationLink) throws Exception;
				
	//U - update
	public abstract void updateById(int id, int idPlan, int idScientificArticles, String articleComments, String publicationLink) throws Exception;
		
	//Filter by Plan
	public abstract ArrayList<ArticlePlan> selectAllArticlePlanByPlan(int idPlan) throws Exception;
		
}

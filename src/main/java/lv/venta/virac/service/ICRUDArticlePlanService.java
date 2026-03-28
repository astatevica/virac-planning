package lv.venta.virac.service;

import java.util.ArrayList;

import lv.venta.virac.dto.ArticlePlanCommentsDTO;
import lv.venta.virac.dto.ArticlePlanReponseDTO;
import lv.venta.virac.dto.ScientificArticlesCommentsDTO;
import lv.venta.virac.model.ArticlePlan;

public interface ICRUDArticlePlanService extends ICRUDBase<ArticlePlan>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE

	//C - create 
	public abstract void create(int idPlan, int idScientificArticles, String articleComments, String publicationLink) throws Exception;
				
	//U - update
	public abstract void updateById(int id, int idPlan, int idScientificArticles, String articleComments, String publicationLink) throws Exception;
		
	//Filter by Plan
	public abstract ArrayList<ArticlePlan> selectAllArticlePlanByPlan(int idPlan) throws Exception;
	
	//C - create autocomplete article
	public abstract void createAutocompleteArticle(int idPlan, int idArticle, ArticlePlanCommentsDTO dto, int employeeId) throws Exception;
	
	//Create new article and attach to Plan
	public abstract ArticlePlanReponseDTO createArticleAndAttachToPlan(int idPlan, ScientificArticlesCommentsDTO articleDTO,
			int employeeId) throws Exception;

	//Delete for User current Plan table
	public abstract void deleteByArticleIdAndPlanId(int idPlan, int idArticle, int employeeId) throws Exception;
	
	//Update for User current Plan table
	public abstract void updateByArticleIdAndPlanId(int idPlan, int idArticle, String articleComments,
			String publicationLink, int employeeId) throws Exception;
		
		
}

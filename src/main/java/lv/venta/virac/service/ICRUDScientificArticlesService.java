package lv.venta.virac.service;

import java.util.ArrayList;

import lv.venta.virac.model.ScientificArticles;

public interface ICRUDScientificArticlesService extends ICRUDBase<ScientificArticles>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE

	//C - create 
	public abstract void create(String name, String coAuthors, int idJournal) throws Exception;
			
	//U - update
	public abstract void updateById(int id, String name, String coAuthors, int idJournal) throws Exception;
	
	//Filter by CoAuthor
	public abstract ArrayList<ScientificArticles> selectAllScientificArticlesByCoauthor(String coAuthors) throws Exception;
	
	//Select all articles by autocomplete
	public abstract ArrayList<ScientificArticles> selectNameAutocomplete(String keyword) throws Exception;
	
		
}

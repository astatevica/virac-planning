package lv.venta.virac.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.ScientificArticles;

public interface IScientificArticlesRepo extends CrudRepository<ScientificArticles, Integer>{
	
	public abstract ArrayList<ScientificArticles> findByCoAuthors(String coAuthors);

}

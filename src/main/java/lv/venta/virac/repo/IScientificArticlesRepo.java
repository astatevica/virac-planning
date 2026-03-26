package lv.venta.virac.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import lv.venta.virac.model.ScientificArticles;

public interface IScientificArticlesRepo extends CrudRepository<ScientificArticles, Integer>{
	
	public abstract ArrayList<ScientificArticles> findByCoAuthors(String coAuthors);
	
	@Query(value = """
			SELECT *
			FROM scientific_articles_table
			WHERE LOWER(name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			""", nativeQuery = true)
			ArrayList<ScientificArticles> searchArticles(@Param("keyword")String keyword);

}

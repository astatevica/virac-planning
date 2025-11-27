package repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.ScientificArticles;

public interface IScientificArticlesRepo extends CrudRepository<ScientificArticles, Integer>{

}

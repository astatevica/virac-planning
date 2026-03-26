package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.model.Journal;
import lv.venta.virac.model.ScientificArticles;
import lv.venta.virac.repo.IJournalRepo;
import lv.venta.virac.repo.IScientificArticlesRepo;
import lv.venta.virac.service.ICRUDScientificArticlesService;

@Service
public class CRUDScientificArticlesImpl implements ICRUDScientificArticlesService{
	
	@Autowired
	private IScientificArticlesRepo scArtRepo;
	
	@Autowired
	private IJournalRepo journRepo;
	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
	public ArrayList<ScientificArticles> retrieveAll() throws Exception {
		Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedScientificArticlesFilter");
        filter.setParameter("isDeleted", false);
        ArrayList<ScientificArticles> articles = (ArrayList<ScientificArticles>) scArtRepo.findAll();
        if (articles.isEmpty()) throw new Exception("There is no articles");
        session.disableFilter("deletedScientificArticlesFilter");
        return articles;
	}

	@Override
	public ScientificArticles retrieveById(int id) throws Exception {
		if (id < 1) throw new Exception("Invalid ID");
        ScientificArticles foundArticles = scArtRepo.findById(id).get();
        if (foundArticles == null) throw new Exception("Articles with the id: (" + id + ") does not exist!");
        
        return foundArticles;
	}

	@Override
	public void deleteById(int id) throws Exception {
		ScientificArticles article = scArtRepo.findById(id).get();
    	if (article == null) throw new Exception("Article with id:"+ id +" does not exist");
    	article.setDeleted(true); // SOFT DELETE
    	scArtRepo.save(article);  // SAVE, NOT DELETE
		
	}

	@Override
	public void create(String name, String coAuthors, int idJournal) throws Exception {
		ArrayList<ScientificArticles> articles = (ArrayList<ScientificArticles>) scArtRepo.findAll();
        
        if(name == null || coAuthors == null || idJournal == 0){
			throw new Exception("The input parameters are incorrect");
		}
        
        Journal journal = journRepo.findById(idJournal).get();
        if(journal == null) {
        	throw new Exception("Journal not found");
        }
        
        for (ScientificArticles art : articles) {
            if (art.getName().equals(name) & art.getCoAuthors().equals(coAuthors) & art.getJournal().getIdJournal()==idJournal & art.isDeleted( )== false) {
                throw new Exception("Scientific article: " + art.getName() + " already exists");
            }
        }

        ScientificArticles article = new ScientificArticles(name, coAuthors, journal);
        scArtRepo.save(article);
		
	}

	@Override
	public void updateById(int id, String name, String coAuthors, int idJournal) throws Exception {
		ScientificArticles article = retrieveById(id);
    	if (article == null) throw new 
    		Exception("Scientific article with (id:" + id + ") does not exist");    	
    	
    	Journal journal = journRepo.findById(idJournal).get();
        if(journal == null) {
        	throw new Exception("Journal not found");
        }
    	
        article.setName(name);
        article.setCoAuthors(coAuthors);
        article.setJournal(journal);
        scArtRepo.save(article);
		
	}

	//TODO: iespējams var uzlabot, lai kaut vai kaut kas atbilstu un tad atgireztu rezultātu, Ieraksta kaut vai tikai viena autora vārdu uzvārdu
	@Override
	public ArrayList<ScientificArticles> selectAllScientificArticlesByCoauthor(String coAuthors) throws Exception {
		ArrayList<ScientificArticles> result = scArtRepo.findByCoAuthors(coAuthors);
		if(result.isEmpty()) {
			throw new Exception("Scientific articles with CoAuthors: " + coAuthors + " does not exist");
		}
		
		return result;
	}

	@Override
	public ArrayList<ScientificArticles> selectNameAutocomplete(String keyword) throws Exception {
		ArrayList<ScientificArticles> result = scArtRepo.searchArticles(keyword);
		return result;
	}

}

package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.model.Journal;
import lv.venta.virac.repo.IJournalRepo;
import lv.venta.virac.service.ICRUDJournalService;

@Service
public class CRUDJournalServiceImpl implements ICRUDJournalService{
	
	@Autowired
	private IJournalRepo journRepo;
	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
	public ArrayList<Journal> retrieveAll() throws Exception {
		Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedJournalFilter");
        filter.setParameter("isDeleted", false);
        ArrayList<Journal> journals = (ArrayList<Journal>) journRepo.findAll();
        if (journals.isEmpty()) throw new Exception("There is no journals");
        session.disableFilter("deletedJournalFilter");
        return journals;
	}

	@Override
	public Journal retrieveById(int id) throws Exception {
		if (id < 1) throw new Exception("Invalid ID");
        Journal foundJournals = journRepo.findById(id).get();
        if (foundJournals == null) throw new Exception("Journal with the id: (" + id + ") does not exist!");
        
        return foundJournals;
	}

	@Override
	public void deleteById(int id) throws Exception {
		Journal journal = journRepo.findById(id).get();
    	if (journal == null) throw new Exception("Journal with id:"+ id +" does not exist");
    	journal.setDeleted(true); // SOFT DELETE
    	journRepo.save(journal);  // SAVE, NOT DELETE
		
	}

	@Override
	public void create(String name) throws Exception {
		ArrayList<Journal> journals = (ArrayList<Journal>) journRepo.findAll();
        
        if(name == null){
			throw new Exception("The input parameters are incorrect");
		}
        
        for (Journal jour : journals) {
            if (jour.getName().equals(name) & jour.isDeleted( )== false) {
                throw new Exception("Journal: " + jour.getName() + " already exists");
            }
        }

        Journal journal = new Journal(name);
        journRepo.save(journal);
		
	}

	@Override
	public void updateById(int id, String name) throws Exception {
		Journal journal = retrieveById(id);
    	if (journal == null) throw new 
    		Exception("Journal with (id:" + id + ") does not exist");
    	
    	journal.setName(name);
        journRepo.save(journal);
		
	}

}

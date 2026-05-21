package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.model.Journal;
import lv.venta.virac.repo.IJournalRepo;
import lv.venta.virac.service.ICRUDJournalService;

@Service
public class CRUDJournalServiceImpl implements ICRUDJournalService{
	
	private IJournalRepo journRepo;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public CRUDJournalServiceImpl (IJournalRepo journRepo) {
		this.journRepo = journRepo;
	}

	@Override
	public ArrayList<Journal> retrieveAll() throws Exception {
		Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedJournalFilter");
        filter.setParameter("isDeleted", false);
        ArrayList<Journal> journals = (ArrayList<Journal>) journRepo.findAll();
        if (journals.isEmpty()) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "There is no journals"
        );
        session.disableFilter("deletedJournalFilter");
        return journals;
	}

	@Override
	public Journal retrieveById(int id) throws Exception {
		if (id < 1) {throw new ResponseStatusException(
	                HttpStatus.BAD_REQUEST,
	                "Invalid ID"
	        );
		}
        return journRepo.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Journal with the id: (" + id + ") does not exist!"
        ));
	}

	@Override
	public void deleteById(int id) throws Exception {
		Journal journal = journRepo.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Journal with the id: (" + id + ") does not exist!"
        ));
    	journal.setDeleted(true); // SOFT DELETE
    	journRepo.save(journal);  // SAVE, NOT DELETE
		
	}

	@Override
	public void create(String name) throws Exception {
		ArrayList<Journal> journals = (ArrayList<Journal>) journRepo.findAll();
        
        if(name == null){
        	throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The input parameters are incorrect"
            );
		}
        
        for (Journal jour : journals) {
            if (jour.getName().equals(name) && jour.isDeleted( )) {
            	throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Journal: " + jour.getName() + " already exists"
                );
            }
        }

        Journal journal = new Journal(name);
        journRepo.save(journal);
		
	}

	@Override
	public void updateById(int id, String name) throws Exception {
		Journal journal = retrieveById(id);
    	if (journal == null) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Journal with (id:" + id + ") does not exist"
        );
    	
    	journal.setName(name);
        journRepo.save(journal);
		
	}

}

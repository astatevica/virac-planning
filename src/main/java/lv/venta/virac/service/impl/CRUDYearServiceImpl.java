package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.model.Year;
import lv.venta.virac.repo.IYearRepo;
import lv.venta.virac.service.ICRUDYearService;

@Service
public class CRUDYearServiceImpl implements ICRUDYearService{
	
	@Autowired
	private IYearRepo yearRepo;
	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
	public ArrayList<Year> retrieveAll() throws Exception {
		Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedYearFilter");
        filter.setParameter("isDeleted", false);
        ArrayList<Year> years = (ArrayList<Year>) yearRepo.findAll();
        if (years.isEmpty()) throw new Exception("There is no year");
        session.disableFilter("deletedYearFilter");
        return years;
	}

	@Override
	public Year retrieveById(int id) throws Exception {
		if (id < 1) throw new Exception("Invalid ID");
        Year foundYear = yearRepo.findById(id).get();
        if (foundYear == null) throw new Exception("Year with the id: (" + id + ") does not exist!");
        
        return foundYear;
	}

	@Override
	public void deleteById(int id) throws Exception {
		Year year = yearRepo.findById(id).get();
    	if (year == null) throw new Exception("Year with id:"+ id +" does not exist");
    	year.setDeleted(true); // SOFT DELETE
    	yearRepo.save(year);  // SAVE, NOT DELETE
	}

	@Override
	public void create(int yearNumber) throws Exception {
		ArrayList<Year> years = (ArrayList<Year>) yearRepo.findAll();
        
        if(yearNumber == 0){
			throw new Exception("The input parameter is incorrect");
		}
        
        for (Year ye : years) {
            if (ye.getYearNumber() == yearNumber & ye.isDeleted( )== false) {
                throw new Exception("Year: " + ye.getYearNumber() + " already exists");
            }
        }

        Year year = new Year(yearNumber);
        yearRepo.save(year);
		
	}

	@Override
	public void updateById(int id, int yearNumber) throws Exception {
		Year year = retrieveById(id);
		ArrayList<Year> years = (ArrayList<Year>) yearRepo.findAll();
    	if (year == null) throw new 
    		Exception("Year with (id:" + id + ") does not exist");    
    	
    	for (Year ye : years) {
            if (ye.getYearNumber() == yearNumber & ye.isDeleted( )== false) {
                throw new Exception("Year: " + ye.getYearNumber() + " already exists");
            }
        }
    	
        year.setYearNumber(yearNumber);
        yearRepo.save(year);
		
	}
	
	

}

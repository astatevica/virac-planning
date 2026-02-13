package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.model.StudentWork;
import lv.venta.virac.model.enums.Degree;
import lv.venta.virac.repo.IStudentWorkRepo;
import lv.venta.virac.service.ICRUDStudentWorkService;

@Service
public class CRUDStudentWorkServiceImpl implements ICRUDStudentWorkService{

	@Autowired
	private IStudentWorkRepo studWorkRepo;
	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
	public ArrayList<StudentWork> retrieveAll() throws Exception {
		Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedStudentWorkFilter");
        filter.setParameter("isDeleted", false);
		ArrayList<StudentWork> studentWork = (ArrayList<StudentWork>) studWorkRepo.findAll();
	    if (studentWork.isEmpty()) throw new Exception("There is no student work");
	    session.disableFilter("deletedStudentWorkFilter");

	    return studentWork;
	}

	@Override
	public StudentWork retrieveById(int id) throws Exception {
		if (id < 1) throw new Exception("Invalid ID");
        StudentWork foundStudentWork = studWorkRepo.findById(id).get();
        if (foundStudentWork == null) throw new Exception("Student work with the id: (" + id + ") does not exist!");
        
        return foundStudentWork;
	}

	@Override
	public void deleteById(int id) throws Exception {
		StudentWork studentWork = studWorkRepo.findById(id).get();
    	if (studentWork == null) throw new Exception("Student work with id:"+ id +" does not exist");
    	studentWork.setDeleted(true); // SOFT DELETE
    	studWorkRepo.save(studentWork);  // SAVE, NOT DELETE
		
	}

	@Override
	public void create(String name, String studentName, String studentSurname, String degree) throws Exception {
		ArrayList<StudentWork> studentWork = (ArrayList<StudentWork>) studWorkRepo.findAll();
        
        if(name == null || studentName == null  || studentSurname == null || degree == null){
			throw new Exception("The input parameters are incorrect");
		}
        
        Degree degreeFound = Degree.valueOf(degree);
        if(degreeFound == null) {
        	throw new Exception("Degree not found");
        }
        
        for (StudentWork sw : studentWork) {
            if (sw.getName().equals(name)& sw.getStudentName().equals(studentName) & sw.getStudentSurname().equals(studentSurname) & 
            		sw.getDegree().equals(degreeFound) & sw.isDeleted( )== false ) {
                throw new Exception("Student work: " + sw.getName()+ " | " + sw.getStudentName() + " " + sw.getStudentSurname() + " already exists");
            }
        }

        StudentWork studWork = new StudentWork(name, studentName, studentSurname, degreeFound);
        studWorkRepo.save(studWork);
		
	}

	@Override
	public void updateById(int id, String name, String studentName, String studentSurname, String degree)
			throws Exception {
		StudentWork studWork = retrieveById(id);
    	if (studWork == null) throw new 
    		Exception("Student work with (id:" + id + ") does not exist");    	
    	
    	Degree degreeFound = Degree.valueOf(degree);
        if(degreeFound == null) {
        	throw new Exception("Degree not found");
        }
    	
        studWork.setName(name);
        studWork.setStudentName(studentName);
        studWork.setStudentSurname(studentSurname);
        studWork.setDegree(degreeFound);
        studWorkRepo.save(studWork);
		
	}

	@Override
	public ArrayList<StudentWork> selectAllStudentWorkByDegree(String degree) throws Exception {
		if (degree == null || degree.isBlank()) {
	        throw new Exception("Degree must not be empty");
	    }

	    Degree degreeFound;
	    try {
	        degreeFound = Degree.valueOf(degree.trim().toLowerCase());
	    } catch (IllegalArgumentException e) {
	        throw new Exception("Invalid degree: " + degree);
	    }

	    ArrayList<StudentWork> result = studWorkRepo.findByDegree(degreeFound);

	    if (result.isEmpty()) {
	        throw new Exception("Student work with degree: " + degree + " does not exist");
	    }

	    return result;
		
	}
	
	
}

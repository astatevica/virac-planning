package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.model.Course;
import lv.venta.virac.repo.ICourseRepo;
import lv.venta.virac.service.ICRUDCourseService;

@Service
public class CRUDCourseServiceImpl implements ICRUDCourseService{
	
	private ICourseRepo courseRepo;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public CRUDCourseServiceImpl (ICourseRepo courseRepo) {
		this.courseRepo = courseRepo;
	}

	@Override
	public ArrayList<Course> retrieveAll() throws Exception {
		Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedCourseFilter");
        filter.setParameter("isDeleted", false);
        ArrayList<Course> courses = (ArrayList<Course>) courseRepo.findAll();
        if (courses.isEmpty()) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "There is no Courses"
        );
        session.disableFilter("deletedCourseFilter");
        return courses;
	}

	@Override
	public Course retrieveById(int id) throws Exception {
		   if (id < 1) {
		        throw new ResponseStatusException(
		                HttpStatus.BAD_REQUEST,
		                "Invalid ID"
		        );
		    }

		    return courseRepo.findById(id)
		            .orElseThrow(() -> new ResponseStatusException(
		                    HttpStatus.NOT_FOUND,
		                    "Course with id: (" + id + ") does not exist!"
		            ));
	}

	@Override
	public void deleteById(int id) throws Exception {
		Course course = courseRepo.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Course with id: (" + id + ") does not exist!"
        ));
    	course.setDeleted(true); // SOFT DELETE
    	courseRepo.save(course);  // SAVE, NOT DELETE
		
	}

	@Override
	public void create(String name, int ectsCredits, String semester, String faculty) throws Exception {
		ArrayList<Course> courses = (ArrayList<Course>) courseRepo.findAll();
        
        if(name == null || ectsCredits == 0 || semester == null || faculty == null){
			throw new ResponseStatusException(
	                HttpStatus.BAD_REQUEST,
	                "The input parameters are incorrect"
	        );
		}
        
        for (Course co : courses) {
            if (co.getName().equals(name) && co.getEctsCredits()==ectsCredits && co.getSemester().equals(semester) && co.getFaculty().equals(faculty) && co.isDeleted( )) {
                throw new ResponseStatusException(
    	                HttpStatus.BAD_REQUEST,
    	                "Course: " + co.getName() + " already exists"
    	        );
            }
        }

        Course course = new Course(name, ectsCredits, semester, faculty);
        courseRepo.save(course);
		
	}

	@Override
	public void updateById(int id, String name, int ectsCredits, String semester, String faculty) {

	    if (id < 1) {
	        throw new ResponseStatusException(
	                HttpStatus.BAD_REQUEST,
	                "Invalid ID"
	        );
	    }

	    Course course = courseRepo.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND,
	                    "Course not found with id: " + id
	            ));

	    course.setName(name);
	    course.setEctsCredits(ectsCredits);
	    course.setSemester(semester);
	    course.setFaculty(faculty);

	    courseRepo.save(course);
	}

	@Override
	public ArrayList<Course> selectNameAutocomplete(String keyword){
		return courseRepo.searchCourses(keyword);
			
	}

}

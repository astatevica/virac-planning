package lv.venta.virac.user;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.model.Employee;
import lv.venta.virac.repo.IEmployeeRepo;

@Service
public class CRUDUserServiceImpl implements ICRUDUserService{
	
	@Autowired
	private IUserRepo userRepo;
	
	@Autowired
	private IEmployeeRepo employeeRepo;
	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
	public ArrayList<User> retrieveAll() throws Exception {
		Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedUserFilter");
        filter.setParameter("isDeleted", false);
        ArrayList<User> users = (ArrayList<User>) userRepo.findAll();
        if (users.isEmpty()) throw new Exception("There is no users");
        session.disableFilter("deletedUserFilter");
        return users;
	}

	@Override
	public User retrieveById(int id) throws Exception {
		if (id < 1) throw new Exception("Invalid ID");
        User foundUser = userRepo.findById(id).get();
        if (foundUser == null) throw new Exception("User with the id: (" + id + ") does not exist!");
        
        return foundUser;
	}

	@Override
	public void deleteById(int id) throws Exception {
		User user = userRepo.findById(id).get();
    	if (user == null) throw new Exception("User with id:"+ id +" does not exist");
    	user.setDeleted(true); // SOFT DELETE
    	userRepo.save(user);  // SAVE, NOT DELETE
		
	}


	@Override
	public void updateById(int id, String firstname, String lastname, String email, String password, String role,
			int idEmployee) throws Exception {
		User user = retrieveById(id);
    	if (user == null) throw new 
    		Exception("User with (id:" + id + ") does not exist");    	
    	
    	Employee emp = employeeRepo.findById(idEmployee).get();
        if(emp == null) {
        	throw new Exception("Employee not found");
        }
            	
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.valueOf(role));
        user.setEmployee(emp);
        userRepo.save(user);
		
	}

}

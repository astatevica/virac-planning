package lv.venta.virac.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.virac.model.ViracDepartment;
import lv.venta.virac.repo.IViracDepartmentRepo;
import lv.venta.virac.service.ICRUDViracDepService;

@Service
public class CRUDViracDepServiceImpl implements ICRUDViracDepService {

    @Autowired
    private IViracDepartmentRepo depRepo;
    
    @Override
    public ArrayList<ViracDepartment> retrieveAll() throws NotContextException {
       ArrayList<ViracDepartment> departmets = (ArrayList<ViracDepartment>) depRepo.findAll();
       if (departmets.isEmpty()) throw new NotContextException("There is no department");

        return departmets;
    }
    
    //Something mby needed with pagable

    @Override
    public ViracDepartment retrieveById(int id) throws Exception {
        if (id < 1) throw new Exception("Invalid ID");
        ViracDepartment foundDepartment = depRepo.findById(id).get();
        if (foundDepartment == null) throw new Exception("Department with the id: (" + id + ") does not exist!");
        
        return foundDepartment;
        
    }

    @Override
    public void create(String name) throws Exception {
        ArrayList<ViracDepartment> departmets = (ArrayList<ViracDepartment>) depRepo.findAll();
        
        for (ViracDepartment dep : departmets) {
            if (dep.getName().equals(name)) {
                throw new Exception("Department with title: " + dep.getName() + " already exists");
            }
        }

        ViracDepartment department = new ViracDepartment();
        department.setName(name);
        depRepo.save(department);
    }

    @Override
    public void updateById(int id, String name) throws Exception {
    	ViracDepartment department = retrieveById(id);
    	if (department == null) throw new 
    		Exception("Event with (id:" + id + ") does not exist");
    	
        department.setName(name);
        depRepo.save(department);
    }

    @Override
    public void deleteById(int id) throws Exception {
    	ViracDepartment department = depRepo.findById(id).get();
    	if (department == null) throw new Exception("Department with id:"+ id +" does not exist");
        depRepo.delete(department);
    }
}

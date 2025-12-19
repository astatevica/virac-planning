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
    public ArrayList<ViracDepartment> retrieveAll() throws Exception {
       if(depRepo.count() == 0) throw new Exception("There are no customers");

        return (ArrayList<ViracDepartment>) depRepo.findAll();
    }

    @Override
    public ViracDepartment retrieveById(int id) throws Exception {
        return depRepo.findById(id)
                .orElseThrow(() -> new Exception("Department not found with id: " + id));
    }

    @Override
    public void create(String name) throws Exception {
        if (name == null || name.isBlank()) {
            throw new Exception("Department name cannot be empty");
        }

        ViracDepartment department = new ViracDepartment();
        department.setName(name);
        depRepo.save(department);
    }

    @Override
    public void updateById(int id, String name) throws Exception {
        ViracDepartment department = retrieveById(id);
        department.setName(name);
        depRepo.save(department);
    }

    @Override
    public void deleteById(int id) throws Exception {
        if (!depRepo.existsById(id)) {
            throw new Exception("Department not found with id: " + id);
        }
        depRepo.deleteById(id);
    }
}

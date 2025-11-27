package lv.venta.virac.repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.Employee;

public interface IEmployeeRepo extends CrudRepository<Employee, Integer>{

}

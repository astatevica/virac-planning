package lv.venta.virac.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.StudentWork;

public interface IStudentWorkRepo extends CrudRepository<StudentWork, Integer>{
	
	public abstract ArrayList<StudentWork> findByDegree(int departmentId);

}

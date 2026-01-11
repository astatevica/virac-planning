package lv.venta.virac.repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.ViracDepartment;

public interface IViracDepartmentRepo extends CrudRepository<ViracDepartment, Integer>{

	public abstract ViracDepartment findByName(String nameDepartment);

	//TODO: needs to be checked
	public abstract void setDeleted(boolean deleted);
}

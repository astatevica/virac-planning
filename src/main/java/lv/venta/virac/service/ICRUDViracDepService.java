package lv.venta.virac.service;

import lv.venta.virac.model.ViracDepartment;

public interface ICRUDViracDepService extends ICRUDBase<ViracDepartment>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE
	
	//C - create 
	public abstract void create(String name, String headName, String headSurname) throws Exception;
	
	//U - update
	public abstract void updateById(int id, String name, String headName, String headSurname) throws Exception;

}

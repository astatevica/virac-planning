package lv.venta.virac.service;

import lv.venta.virac.model.Year;

public interface ICRUDYearService extends ICRUDBase<Year>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE

	//C - create 
	public abstract void create(int yearNumber) throws Exception;
					
	//U - update
	public abstract void updateById(int id, int yearNumber) throws Exception;
}

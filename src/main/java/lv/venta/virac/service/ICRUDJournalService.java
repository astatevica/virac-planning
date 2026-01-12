package lv.venta.virac.service;

import lv.venta.virac.model.Journal;

public interface ICRUDJournalService extends ICRUDBase<Journal>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE

	//C - create 
	public abstract void create(String name) throws Exception;
			
	//U - update
	public abstract void updateById(int id, String name) throws Exception;
		
}

package lv.venta.virac.service;

import java.util.ArrayList;

public interface ICRUDBase <Ttype>{

		//R - retrieve all
		public abstract ArrayList<Ttype> retrieveAll(boolean isDeleted) throws Exception;
		
		//R - retrieve by id
		public abstract Ttype retrieveById(int id) throws Exception;
		
		//D - delete
		public abstract void deleteById(int id) throws Exception;
} 

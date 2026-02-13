package lv.venta.virac.user;

import lv.venta.virac.service.ICRUDBase;

public interface ICRUDUserService extends ICRUDBase<User>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE
				
	//U - update
	public abstract void updateById(int id, String firstname, String lastname,String email, String password, 
			String role, int idEmployee) throws Exception;
	
}

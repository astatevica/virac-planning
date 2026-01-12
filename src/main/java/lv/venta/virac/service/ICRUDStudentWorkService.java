package lv.venta.virac.service;

import java.util.ArrayList;

import lv.venta.virac.model.StudentWork;

public interface ICRUDStudentWorkService extends ICRUDBase<StudentWork>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE

	//C - create 
	public abstract void create(String name, String studentName, String studentSurname, String degree) throws Exception;
			
	//U - update
	public abstract void updateById(int id, String name, String studentName, String studentSurname, String degree) throws Exception;
		
	//Filter by Degree
	public abstract ArrayList<StudentWork> selectAllStudentWorkByDegree(String degree) throws Exception;

	}

package lv.venta.virac.service;

import java.util.ArrayList;

import lv.venta.virac.model.Course;

public interface ICRUDCourseService extends ICRUDBase<Course>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE

	//C - create 
	public abstract void create(String name, int ectsCredits, String semester, String faculty) throws Exception;
					
	//U - update
	public abstract void updateById(int id, String name, int ectsCredits, String semester, String faculty) throws Exception;
	
	//Select all courses by autocomplete
	public abstract ArrayList<Course> selectNameAutocomplete(String keyword) throws Exception;


}

package lv.venta.virac.dto;

import lv.venta.virac.model.ViracDepartment;

public class EmployeeDTO {
	
	private int idEmployee;
	private String name;
	private String surname;
	private ViracDepartment viracDepartment;
	private String position;
	
    public EmployeeDTO() {
    }
	
	public EmployeeDTO(int idEmployee, String name, String surname,ViracDepartment viracDepartment, String position) {
		this.idEmployee = idEmployee;
		this.name = name;
		this.surname = surname;
		this.viracDepartment = viracDepartment;
		this.position = position;
	}
	
	public int getId() {
		return idEmployee;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
        this.name = name;
    }
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
        this.surname = surname;
    }
	
	public ViracDepartment getDepartment() {
		return viracDepartment;
	}
	
	public void setDepartment(ViracDepartment department) {
        this.viracDepartment = department;
    }
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
        this.position = position;
    }
	

  

}

package lv.venta.virac.dto;

public class EmployeeDTO {
	
	private int idEmployee;
	private String name;
	private String surname;
	private String nameDepartment;
	private String position;
	
    public EmployeeDTO() {
    }
	
	public EmployeeDTO(int idEmployee, String name, String surname,String nameDepartment, String position) {
		this.idEmployee = idEmployee;
		this.name = name;
		this.surname = surname;
		this.nameDepartment = nameDepartment;
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
	
	public String getNameDepartment() {
		return nameDepartment;
	}
	
	public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
        this.position = position;
    }
	

  

}

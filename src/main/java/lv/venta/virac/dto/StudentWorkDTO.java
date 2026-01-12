package lv.venta.virac.dto;

public class StudentWorkDTO {
	
	private int idStudWork;
	private String name;
	private String studentName;
	private String studentSurname;
	private String degree;
	
	public StudentWorkDTO() {
		
	}
	
	public StudentWorkDTO(int idStudWork, String name, String studentName, String studentSurname, String degree) {
		this.idStudWork = idStudWork;
		this.name = name;
		this.studentName = studentName;
		this.studentSurname = studentSurname;
		this.degree = degree;
	}

	public int getIdStudWork() {
		return idStudWork;
	}

	public void setIdStudWork(int idStudWork) {
		this.idStudWork = idStudWork;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentSurname() {
		return studentSurname;
	}

	public void setStudentSurname(String studentSurname) {
		this.studentSurname = studentSurname;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}
	

}

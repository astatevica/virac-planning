package lv.venta.virac.dto;

public class CourseDTO {
	
	private int idCourse;
	private String name;
	private int ectsCredits;
	private String semester;
	private String faculty;
	
	public CourseDTO() {
		
	}
	
	public CourseDTO(int idCourse, String name, int ectsCredits, String semester, String faculty) {
		this.idCourse = idCourse;
		this.name = name;
		this.ectsCredits = ectsCredits;
		this.semester = semester;
		this.faculty = faculty;
	}
	
	public int getIdCourse() {
		return idCourse;
	}
	public void setIdCourse(int idCourse) {
		this.idCourse = idCourse;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEctsCredits() {
		return ectsCredits;
	}
	public void setEctsCredits(int ectsCredits) {
		this.ectsCredits = ectsCredits;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	
	

}

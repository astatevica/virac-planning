package lv.venta.virac.dto;

public class YearDTO {
	
	private int idYear;
	private int yearNumber;
	
	public YearDTO() {
		
	}
	
	public YearDTO(int idYear, int yearNumber) {
		this.idYear = idYear;
		this.yearNumber = yearNumber;
	}
	
	public int getIdYear() {
		return idYear;
	}
	public void setIdYear(int idYear) {
		this.idYear = idYear;
	}
	
	public int getYearNumber() {
		return yearNumber;
	}
	public void setYearNumber(int yearNumber) {
		this.yearNumber = yearNumber;
	}

}

package lv.venta.virac.dto;

public class DepartmentDTO {
	
	private int idDepartment;
    private String name;

    public DepartmentDTO(int id, String name) {
        this.idDepartment = id;
        this.name = name;
    }

    public int getId() { return idDepartment; }
    public String getName() { return name; }

}

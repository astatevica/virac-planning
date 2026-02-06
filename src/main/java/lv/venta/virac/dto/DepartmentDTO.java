package lv.venta.virac.dto;

public class DepartmentDTO {

	private int id;
    private String name;
    private String headName;
    private String headSurname;
    
    public DepartmentDTO() {
    	
    }

    public DepartmentDTO(int id, String name, String headName, String headSurname) {
        this.id = id;
        this.name = name;
        this.headName = headName;
        this.headSurname = headSurname;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

	public String getHeadName() {
		return headName;
	}

	public String getHeadSurname() {
		return headSurname;
	}
}

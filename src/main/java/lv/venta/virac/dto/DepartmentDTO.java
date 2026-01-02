package lv.venta.virac.dto;

public class DepartmentDTO {

	private int id;
    private String name;

    public DepartmentDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

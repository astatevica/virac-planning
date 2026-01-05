package lv.venta.virac.dto;

import jakarta.validation.constraints.NotBlank;

public class DepartmentRequestDTO {

    @NotBlank(message = "Department name cannot be empty")
    private String name;

    //REQUIRED
    public DepartmentRequestDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

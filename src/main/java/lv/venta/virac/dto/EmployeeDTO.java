package lv.venta.virac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
	
	private int idEmployee;
	private String name;
	private String surname;
	private String nameDepartment;
	private String position;

}

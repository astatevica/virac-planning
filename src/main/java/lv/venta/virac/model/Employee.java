package lv.venta.virac.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "employeeTable")
@ToString
@Entity
//TODO: visur salikt anotācijas
public class Employee {

	private int idEmployee;
	
	private String name;
	
	private String surname;
	
	//TODO: būs jāsavieno
	//private int idDepartment;
	
	private String position;
}

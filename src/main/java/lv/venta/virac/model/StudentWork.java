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
@Table(name = "studentWorkTable")
@ToString
@Entity
public class StudentWork {

	private int idStudWork;
	
	private String name;
	
	private String studentName;
	
	private String studentSurname;
	
	//TODO: make enum
	//private enum degree;
}

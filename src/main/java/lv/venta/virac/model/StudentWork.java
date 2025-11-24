package lv.venta.virac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.venta.virac.model.enums.Degree;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "studentWorkTable")
@ToString
@Entity
public class StudentWork {
	@Id
	@Column(name = "idStudWork")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idStudWork;
	
	@Column(name = "name")
	@NotNull
	@Size(max = 20, min = 2)
	private String name;
	
	@Column(name = "studentName")
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ' ]+", message = "Tikai burti un atstarpes ir atlautas")
	@Size(max = 20, min = 2)
	private String studentName;
	
	@Column(name = "studentSurname")
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ' ]+", message = "Tikai burti un atstarpes ir atlautas")
	@Size(max = 20, min = 2)
	private String studentSurname;
	
	@NotNull
	@Column(name = "Degree")
	private Degree degree;
	
	public StudentWork(String name, String studentName, String studentSurname, Degree degree) {
		setName(name);
		setStudentName(studentName);
		setStudentSurname(studentSurname);
		setDegree(degree);
	}
}

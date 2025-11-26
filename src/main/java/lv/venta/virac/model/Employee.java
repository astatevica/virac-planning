package lv.venta.virac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
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
public class Employee {
	
	@Id
	@Column(name = "idEmployee")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idEmployee;
	
	@Column(name = "name")
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ' ]+", message = "Tikai burti un atstarpes ir atlautas")
	@Size(max = 20, min = 2)
	private String name;
	
	@Column(name = "surname")
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ' ]+", message = "Tikai burti un atstarpes ir atlautas")
	@Size(max = 20, min = 2)
	private String surname;
	
	@ManyToOne
	@JoinColumn(name = "idDepartment")
	private ViracDepartment viracDepartment;
	
	@Column(name = "adress")
	@NotNull
	private String position;
	
	@OneToMany(mappedBy = "projectManagement")
	@ToString.Exclude
	private ProjectManagement projectManagement;
	
	public Employee(String name, String surname,ViracDepartment viracDepartment, String position) {
		setName(name);
		setSurname(surname);
		setViracDepartment(viracDepartment);
		setPosition(position);
	}
}

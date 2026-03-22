package lv.venta.virac.model;

import java.util.Collection;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@SQLDelete(sql = "UPDATE student_work_table SET deleted = true WHERE id_stud_work=?")
@FilterDef(name = "deletedStudentWorkFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedStudentWorkFilter", condition = "deleted = :isDeleted")
public class StudentWork extends Auditable{
	@Id
	@Column(name = "idStudWork")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idStudWork;
	
	@Column(name = "name")
	//@NotNull
	//@Size(max = 20, min = 2)
	private String name;
	
	@Column(name = "studentName")
	@NotNull
	//@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ' ]+", message = "Tikai burti un atstarpes ir atlautas")
	//@Size(max = 20, min = 2)
	private String studentName;
	
	@Column(name = "studentSurname")
	@NotNull
	//@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ' ]+", message = "Tikai burti un atstarpes ir atlautas")
	//@Size(max = 20, min = 2)
	private String studentSurname;
	
	@NotNull
	@Column(name = "Degree", nullable = false)
	@Enumerated(EnumType.STRING)
	private Degree degree;
	
	@OneToMany(mappedBy = "studentWork")
	@ToString.Exclude
	private Collection<WorkPlan> workPlan;
	
	@Column(name = "deleted")
	private boolean deleted = Boolean.FALSE;
	
	public StudentWork(String name, String studentName, String studentSurname, Degree degree) {
		setName(name);
		setStudentName(studentName);
		setStudentSurname(studentSurname);
		setDegree(degree);
	}
}

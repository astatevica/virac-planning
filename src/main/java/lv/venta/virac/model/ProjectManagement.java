package lv.venta.virac.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "projectManagementTable")
@ToString
@Entity
public class ProjectManagement {
	
	@Id
	@Column(name = "idProjectManag")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idProjectManag;
	
	@ManyToOne
	@JoinColumn(name = "idEmployee")
	private Employee employee;
	
	//TODO: jāsataisa kolekcija ar employees
//	@OneToMany
//	@JoinColumn(name = "idEmployee")
//	private Collection<Employee> employee;
	
	//TODO: problēmas ar formatējumu
	@Column(name = "startDate")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	
	@Column(name = "endDate")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date endDate;
	
	@OneToOne(mappedBy = "projectManagement")
	@ToString.Exclude
	private Project project;
	
	public ProjectManagement(Employee employee, Date startDate, Date endDate) {
		setEmployee(employee);
		setStartDate(startDate);
		setEndDate(endDate);
	}
}

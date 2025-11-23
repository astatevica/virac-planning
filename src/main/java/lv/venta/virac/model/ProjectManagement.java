package lv.venta.virac.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
	
	@OneToMany
	@JoinColumn(name = "idEmployee")
	private Employee idEmployee;
	
	@OneToOne
	@JoinColumn(name = "idProject")
	private Project idProject;
	
	@NotNull
	@Column(name = "startDate")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	
	@NotNull
	@Column(name = "endDate")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date endDate;
	
	public ProjectManagement(Employee idEmployee, Project idProject, Date startDate, Date endDate) {
		setIdEmployee(idEmployee);
		setIdProject(idProject);
		setStartDate(startDate);
		setEndDate(endDate);
	}
}

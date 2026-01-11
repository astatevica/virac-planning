package lv.venta.virac.model;

import java.time.LocalDate;
import java.util.Collection;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
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
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "projectTable")
@ToString
@Entity
@SQLDelete(sql = "UPDATE project_table SET deleted = true WHERE id_project=?")
@FilterDef(name = "deletedProjectFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedProjectFilter", condition = "deleted = :isDeleted")
public class Project {

	@Id
	@Column(name = "idProject")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idProject;
	
	@Column(name = "name")
	@NotNull
	@Size(max = 50, min = 2)
	private String name;
	
	@Column(name = "number")
	@NotNull
	private int number;
	
	@OneToOne
	@JoinColumn(name = "idProjectManag")
	private ProjectManagement projectManagement;
	
	@NotNull
	@Column(name = "startDate")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate startDate;
	
	@NotNull
	@Column(name = "endDate")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate endDate;
	
	@Column(name = "acronym")
	@NotNull
	@Size(max = 10, min = 2)
	private String acronym;
	
	@OneToMany(mappedBy = "project")
	@ToString.Exclude
	private Collection<ProjectPlan> projectPlan;
	
	@Column(name = "deleted")
	private boolean deleted = Boolean.FALSE;
	
	public Project(String name, int number, ProjectManagement projectManagement, LocalDate startDate, LocalDate endDate, String acronym){
		setName(name);
		setNumber(number);
		setProjectManagement(projectManagement);
		setStartDate(startDate);
		setEndDate(endDate);
		setAcronym(acronym);
	}
}

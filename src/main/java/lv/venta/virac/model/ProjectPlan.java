package lv.venta.virac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
@Table(name = "projectPlanTable")
@ToString
@Entity
public class ProjectPlan {
	
	@Id
	@Column(name = "idProjectPlan")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idProjectPlan;
	
	@OneToMany
	@JoinColumn(name = "idPlan")
	private Plan idPlan;
	
	@OneToMany
	@JoinColumn(name = "idProject")
	private Project idProject;
	
	@Column(name = "name")
	@NotNull
	@Size(max = 200, min = 2)
	private String tasks;
	
	@Column(name = "name")
	@NotNull
	@Size(max = 200, min = 2)
	private String workDone;
	
	public ProjectPlan(Plan idPlan, Project idProject, String tasks, String workDone) {
		setIdPlan(idPlan);
		setIdProject(idProject);
		setTasks(tasks);
		setWorkDone(workDone);
	}
}

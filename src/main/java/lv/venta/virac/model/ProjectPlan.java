package lv.venta.virac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@ManyToOne
	@JoinColumn(name = "idPlan")
	private Plan plan;
	
	@ManyToOne
	@JoinColumn(name = "idProject")
	private Project project;
	
	@Column(name = "name")
	@NotNull
	@Size(max = 200, min = 2)
	private String tasks;
	
	@Column(name = "name")
	@NotNull
	@Size(max = 200, min = 2)
	private String workDone;
	
	public ProjectPlan(Plan plan, Project project, String tasks, String workDone) {
		setPlan(plan);
		setProject(project);
		setTasks(tasks);
		setWorkDone(workDone);
	}
}

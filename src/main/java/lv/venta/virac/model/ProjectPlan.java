package lv.venta.virac.model;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@SQLDelete(sql = "UPDATE project_plan_table SET deleted = true WHERE id_project_plan=?")
@FilterDef(name = "deletedProjectPlanFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedProjectPlanFilter", condition = "deleted = :isDeleted")
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
	
	@Column(name = "tasks")
	//@NotNull
	//@Size(max = 200, min = 2)
	private String tasks;
	
	@Column(name = "workDone")
	//@NotNull
	//@Size(max = 200, min = 2)
	private String workDone;
	
	@Column(name = "deleted")
	private boolean deleted = Boolean.FALSE;
	
	public ProjectPlan(Plan plan, Project project, String tasks, String workDone) {
		setPlan(plan);
		setProject(project);
		setTasks(tasks);
		setWorkDone(workDone);
	}
}

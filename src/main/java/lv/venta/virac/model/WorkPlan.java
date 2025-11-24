package lv.venta.virac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "workPlanTable")
@ToString
@Entity
public class WorkPlan {
	@Id
	@Column(name = "idWorkPlan")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idWorkPlan;
	
	@OneToMany
	@JoinColumn(name = "idStudWork")
	private StudentWork idStudWork;
	
	@OneToMany
	@JoinColumn(name = "idPlan")
	private Plan idPlan;
	
	@Column(name = "workDone")
	private String workDone;
	
	public WorkPlan(StudentWork idStudWork,Plan idPlan,String workDone) {
		setIdStudWork(idStudWork);
		setIdPlan(idPlan);
		setWorkDone(workDone);
	}
}

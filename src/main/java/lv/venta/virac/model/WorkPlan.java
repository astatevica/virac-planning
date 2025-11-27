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
	
	@ManyToOne
	@JoinColumn(name = "idStudWork")
	private StudentWork studentWork;
	
	@OneToMany
	@JoinColumn(name = "idPlan")
	private Plan idPlan;
	
	@Column(name = "workDone")
	private String workDone;
	
	public WorkPlan(StudentWork studentWork,Plan idPlan,String workDone) {
		setStudentWork(studentWork);
		setIdPlan(idPlan);
		setWorkDone(workDone);
	}
}

package lv.venta.virac.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

	private int idWorkPlan;
	
	//TODO: connect
	private int idStudWork;
	
	//TODO: connect
	private int idPlan;
	
	private String workDone;
}

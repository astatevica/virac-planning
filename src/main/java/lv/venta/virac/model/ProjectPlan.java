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
@Table(name = "projectPlanTable")
@ToString
@Entity
public class ProjectPlan {
	private int idProjectPlan;
	
	//TODO:connect
	private int idPlan;
	
	//TODO:connect
	private int idProject;
	
	private String tasks;
	
	private String workDone;
}

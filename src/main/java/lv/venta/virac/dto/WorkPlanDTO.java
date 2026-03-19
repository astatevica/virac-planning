package lv.venta.virac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlanDTO {
	
	private int idWorkPlan;
	private int idStudWork;
	private int idPlan;
	private String workDone;

}

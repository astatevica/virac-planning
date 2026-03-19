package lv.venta.virac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoursePlanDTO {
	
	private int idCoursePlan;
	private int idPlan;
	private int idCourse;
	private String workDone;

}

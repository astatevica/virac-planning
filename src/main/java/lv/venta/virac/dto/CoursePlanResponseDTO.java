package lv.venta.virac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CoursePlanResponseDTO {
	
	private String name;
	private int ectsCredits;
	private String semester;
	private String faculty;
	private String workDone;

}

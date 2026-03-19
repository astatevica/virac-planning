package lv.venta.virac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
	
	private int idCourse;
	private String name;
	private int ectsCredits;
	private String semester;
	private String faculty;

}

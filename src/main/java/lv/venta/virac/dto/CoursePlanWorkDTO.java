package lv.venta.virac.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoursePlanWorkDTO {
	
	//Can be empty if coming from new course entity
	private int idCourse;
	
	@NotBlank(message = "Name is required")
	@Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
	private String name;
	
	@Min(value = 2, message = "ectsCredits must be at least 2")
	private int ectsCredits;
	
	@NotBlank(message = "Semester is required")
	@Size(min = 3, max = 50, message = "Semester must be between 3 and 50 characters")
	private String semester;
	
	@NotBlank(message = "Faculty is required")
	@Size(min = 3, max = 50, message = "Faculty must be between 3 and 50 characters")
	private String faculty;
	
	@NotBlank(message = "Work Done required")
	@Size(min = 2, max = 150, message = "Work Done must be between 2 and 50 characters")
	private String workDone;
}

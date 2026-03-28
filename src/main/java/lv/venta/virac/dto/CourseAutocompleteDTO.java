package lv.venta.virac.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CourseAutocompleteDTO {
	
	@Min(value = 1, message = "idCourse must not be empty")
	private int idCourse;
	
	@Min(value = 1, message = "idPlan must not be empty")
	private int idPlan;
	
	@NotBlank(message = "Work Done required")
	@Size(min = 2, max = 150, message = "Work Done must be between 2 and 50 characters")
    private String workDone;

}

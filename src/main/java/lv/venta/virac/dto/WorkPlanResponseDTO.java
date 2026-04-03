package lv.venta.virac.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.virac.model.enums.Degree;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlanResponseDTO {
	
	//Can be empty if coming from new course entity
	private int idStudWork;
	
	@NotBlank(message = "Work name is required")
	@Size(min = 3, max = 50, message = "Work name must be between 3 and 50 characters")
	private String name;
	
	@NotBlank(message = "Student name is required")
	@Size(min = 3, max = 15, message = "Student name must be between 3 and 15 characters")
	private String studentName;
	
	@NotBlank(message = "Student surname is required")
	@Size(min = 3, max = 15, message = "Student surname must be between 3 and 15 characters")
	private String studentSurname;
	
	@NotNull(message = "Degree is required")
	private Degree degree;
	
	@NotBlank(message = "Work Done required")
	@Size(min = 2, max = 150, message = "Work Done must be between 2 and 50 characters")
	private String workDone;

}

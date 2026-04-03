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
public class ProjectAutocompleteDTO {

	@Min(value = 1, message = "idProject must not be empty")
	private int idProject;
	
	@Min(value = 1, message = "idPlan must not be empty")
	private int idPlan;
	
	@NotBlank(message = "Tasks required")
	@Size(min = 2, max = 150, message = "Tasks must be between 2 and 150 characters")
    private String tasks;
	
	@NotBlank(message = "Work Done required")
	@Size(min = 2, max = 150, message = "Work Done must be between 2 and 150 characters")
    private String workDone;
}

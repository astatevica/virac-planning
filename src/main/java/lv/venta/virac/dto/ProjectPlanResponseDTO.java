package lv.venta.virac.dto;

import java.time.LocalDate;

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
public class ProjectPlanResponseDTO {
	
	@Min(value = 1, message = "idPlan must not be empty")
	private int idPlan;
	
	@Min(value = 1, message = "idProject must not be empty")
	private int idProject;
	
	@NotBlank(message = "Name is required")
	@Size(min = 3, max = 150, message = "Name must be between 3 and 150 characters")
	private String name;
	
	@Min(value = 10000, message = "number must be at least 10000")
	private int number;
	
	@Min(value = 1, message = "managementId must be at least 2")
	private int managementId;
	
	//@NotBlank(message = "Semester is required")
	//TODO: can add date validation
	private LocalDate startDate;
	
	//@NotBlank(message = "Faculty is required")
	//TODO: can add date validation
	private LocalDate endDate;
	
	@NotBlank(message = "Acronym required")
	@Size(min = 2, max = 150, message = "Acronym must be between 2 and 50 characters")
	private String acronym;
	
	@NotBlank(message = "Tasks required")
	@Size(min = 2, max = 150, message = "Tasks must be between 2 and 50 characters")
	private String tasks;
	
	@NotBlank(message = "Work Done required")
	@Size(min = 2, max = 150, message = "Work Done must be between 2 and 50 characters")
	private String workDone;

}

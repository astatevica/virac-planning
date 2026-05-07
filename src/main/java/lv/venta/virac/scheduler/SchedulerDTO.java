package lv.venta.virac.scheduler;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SchedulerDTO {
	
	@NotBlank(message = "Year required")
	private int yearId;
	@NotBlank(message = "Planned Freeze Date required")
	private LocalDate plannedFreezeDate;
	@NotBlank(message = "Done Freeze Date required")
	private LocalDate doneFreezeDate;

}

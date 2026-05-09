package lv.venta.virac.scheduler;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SchedulerDTO {
	
	private int idYear;
	private LocalDate plannedFreezeDate;
	private LocalDate doneFreezeDate;

}

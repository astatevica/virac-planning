package lv.venta.virac.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectManagementDTO {

	private int idProjectManag;
	private int employeeId;
	private LocalDate startDate;
	private LocalDate endDate;
	
}

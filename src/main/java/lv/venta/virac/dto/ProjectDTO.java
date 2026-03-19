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
public class ProjectDTO {
	
	private int idProject;
	private String name;
	private int number;
	private int managementId;
	private LocalDate startDate;
	private LocalDate endDate;
	private String acronym;

}

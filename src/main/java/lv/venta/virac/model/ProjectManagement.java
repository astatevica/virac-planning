package lv.venta.virac.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "projectManagementTable")
@ToString
@Entity
public class ProjectManagement {
	
	private int idProjectManag;
	
	//TODO: connect
	private int idEmployee;
	
	//TODO: connect
	private int idProject;
	
	private Date startDate;
	
	private Date endDate;
}

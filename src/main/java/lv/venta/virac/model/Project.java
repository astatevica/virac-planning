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
@Table(name = "projectTable")
@ToString
@Entity
public class Project {

	private int idProject;
	
	private String name;
	
	private int number;
	
	//TODO: connect
	private int idProjectManag;
	
	private Date startDate;
	
	private Date endDate;
	
	private String acronym;
}

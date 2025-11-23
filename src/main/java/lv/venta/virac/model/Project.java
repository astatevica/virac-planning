package lv.venta.virac.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
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

	@Id
	@Column(name = "idProject")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idProject;
	
	@Column(name = "name")
	@NotNull
	@Size(max = 50, min = 2)
	private String name;
	
	@Column(name = "number")
	@NotNull
	private int number;
	
	//TODO: connect
	private int idProjectManag;
	
	@NotNull
	@Column(name = "startDate")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	
	@NotNull
	@Column(name = "endDate")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date endDate;
	
	@Column(name = "acronym")
	@NotNull
	@Size(max = 10, min = 2)
	private String acronym;
}

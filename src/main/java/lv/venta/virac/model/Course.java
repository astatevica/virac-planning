package lv.venta.virac.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "courseTable")
@ToString
@Entity
public class Course {

	private int idCourse;
	private String name;
	private int ectsCredits;
	private String semester;
	private String faculty;
}

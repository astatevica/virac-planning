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
@Table(name = "coursePlanTable")
@ToString
@Entity
public class CoursePlan {

	private int idCoursePlan;
	private int idPlan;
	private int idCourse;
	private String workDone;
}

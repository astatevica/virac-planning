package lv.venta.virac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
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
	@Id
	@Column(name = "idCoursePlan")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idCoursePlan;
	
	@ManyToOne
	@JoinColumn(name = "idPlan")
	private Plan plan;
	
	@ManyToOne
	@JoinColumn(name = "idCourse")
	private Course course;
	
	@Column(name = "workDone")
	private String workDone;
	
	public CoursePlan(Plan plan, Course course, String workDone) {
		setPlan(plan);
		setCourse(course);
		setWorkDone(workDone);
	}
}

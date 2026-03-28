package lv.venta.virac.model;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
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
@SQLDelete(sql = "UPDATE course_plan_table SET deleted = true WHERE id_course_plan=?")
@FilterDef(name = "deletedCoursePlanFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedCoursePlanFilter", condition = "deleted = :isDeleted")
public class CoursePlan extends Auditable{
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
	@Size(min = 3)
	private String workDone;
	
	@Column(name = "deleted")
	private boolean deleted = Boolean.FALSE;
	
	public CoursePlan(Plan plan, Course course, String workDone) {
		setPlan(plan);
		setCourse(course);
		setWorkDone(workDone);
	}
}

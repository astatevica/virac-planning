package lv.venta.virac.model;

import java.time.LocalDateTime;
import java.util.Collection;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "courseTable")
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE course_table SET deleted = true WHERE id_course=?")
@FilterDef(name = "deletedCourseFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedCourseFilter", condition = "deleted = :isDeleted")
public class Course {
	@Id
	@Column(name = "idCourse")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idCourse;
	
	@Column(name = "name")
	@NotNull
	@Size(max = 50, min = 2)
	private String name;
	
	@Column(name = "ectsCredits")
	@NotNull
	private int ectsCredits;
	
	@Column(name = "semester")
	@NotNull
	private String semester;
	
	@Column(name = "faculty")
	@NotNull
	private String faculty;
	
	@OneToMany(mappedBy = "course")
	@ToString.Exclude
	private Collection<CoursePlan> coursePlan;
	
	@Column(nullable = true,updatable = false)
	@JsonIgnore
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Column(insertable = false)
	@JsonIgnore
	private LocalDateTime lastModified;
	
	@CreatedBy
	@Column(updatable = false)
	@JsonIgnore
	private Integer createdBy;
	
	@LastModifiedBy
	@Column(insertable = false)
	@JsonIgnore
	private Integer lastModifiedBy;
	
	@Column(name = "deleted")
	private boolean deleted = Boolean.FALSE;
	
	public Course(String name, int ectsCredits, String semester, String faculty) {
		setName(name);
		setEctsCredits(ectsCredits);
		setSemester(semester);
		setFaculty(faculty);
	}
}

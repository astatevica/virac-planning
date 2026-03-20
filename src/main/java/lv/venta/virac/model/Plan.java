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
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.venta.virac.model.enums.PlanStatus;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "planTable")
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE plan_table SET deleted = true WHERE id_plan=?")
@FilterDef(name = "deletedPlanFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedPlanFilter", condition = "deleted = :isDeleted")
public class Plan {
	@Id
	@Column(name = "idPlan")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idPlan;
	
	@ManyToOne
	@JoinColumn(name = "idEmployee")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name = "idYear")
	private Year year;
	
	@Column(name = "numOfProjects")
	private int numOfProjects;
	
	@OneToMany(mappedBy = "plan")
	@ToString.Exclude
	private Collection<ProjectPlan> projectPlan;
	
	@Column(name = "numOfArticles")
	private int numOfArticles;
	
	@OneToMany(mappedBy = "plan")
	@ToString.Exclude
	private Collection<ArticlePlan> articlePlan;
	
	@Column(name = "partInConf")
	//@Size(max = 200, min = 2)
	private String partInConf;
	
	//Q: Vai te var nelikt @NotNull?
	@Column(name = "partInConfEnd")
	//@Size(max = 200, min = 2)
	private String partInConfEnd;
	
	@Column(name = "comAbConf")
	//@Size(max = 200, min = 2)
	private String comAbConf;
	
	@Column(name = "comAbConfEnd")
	//@Size(max = 200, min = 2)
	private String comAbConfEnd;
	
	@Column(name = "numOfCourses")
	private int numOfCourses;
	
	@OneToMany(mappedBy = "plan")
	@ToString.Exclude
	private Collection<CoursePlan> coursePlan;
	
	@Column(name = "numOfStudWork")
	@NotNull
	private int numOfStudWork;
	
	@OneToMany(mappedBy = "plan")
	@ToString.Exclude
	private Collection<WorkPlan> workPlan;
	
	@Column(name = "promoOfResearch")
	//@Size(max = 200, min = 2)
	private String promoOfResearch;
	
	@Column(name = "promoOfResearchEnd")
	//@Size(max = 200, min = 2)
	private String promoOfResearchEnd;
	
	@Column(name = "adminWork")
	//@Size(max = 200, min = 2)
	private String adminWork;
	
	@Column(name = "adminWorkEnd")
	//@Size(max = 200, min = 2)
	private String adminWorkEnd;
	
	@Column(name = "projApplicSub")
	//@Size(max = 200, min = 2)
	private String projApplicSub;
	
	@Column(name = "projApplicSubEnd")
	//@Size(max = 200, min = 2)
	private String projApplicSubEnd;
	
	@Column(name = "skillsDevelopment")
	//@Size(max = 200, min = 2)
	private String skillsDevelopment;
	
	@Column(name = "skillsDevelopmentEnd")
	//@Size(max = 200, min = 2)
	private String skillsDevelopmentEnd;
	
	@Column(name = "participationInSeminars")
	//@Size(max = 200, min = 2)
	private String participationInSeminars;
	
	@Column(name = "participationInSeminarsEnd")
	//@Size(max = 200, min = 2)
	private String participationInSeminarsEnd;
	
	@Column(name = "otherJobs")
	//@Size(max = 200, min = 2)
	private String otherJobs;
	
	@Column(name = "otherJobsEnd")
	//@Size(max = 200, min = 2)
	private String otherJobsEnd;
	
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
	
	//PlanStatus
	@NotNull
	@Column(name = "planStatus", nullable = false)
	@Enumerated(EnumType.STRING)
	private PlanStatus planStatus;
	
	public Plan(Employee employee, Year year, int numOfProjects, int numOfArticles, String partInConf, String partInConfEnd,
			String comAbConf, String comAbConfEnd, int numOfCourses, int numOfStudWork, String promoOfResearch, String promoOfResearchEnd,
			String adminWork, String adminWorkEnd, String projApplicSub, String projApplicSubEnd, String skillsDevelopment, 
			String skillsDevelopmentEnd,String participationInSeminars, String participationInSeminarsEnd, String otherJobs, String otherJobsEnd,
			PlanStatus planStatus) {
		setEmployee(employee);
		setYear(year);
		setNumOfProjects(numOfProjects);
		setNumOfArticles(numOfArticles);
		setPartInConf(partInConf);
		setPartInConfEnd(partInConfEnd);
		setComAbConf(comAbConf);
		setComAbConfEnd(comAbConfEnd);
		setNumOfCourses(numOfCourses);
		setNumOfStudWork(numOfStudWork);
		setPromoOfResearch(promoOfResearch);
		setPromoOfResearchEnd(promoOfResearchEnd);
		setAdminWork(adminWork);
		setAdminWorkEnd(adminWorkEnd);
		setProjApplicSub(projApplicSub);
		setProjApplicSubEnd(projApplicSubEnd);
		setSkillsDevelopment(skillsDevelopment);
		setSkillsDevelopmentEnd(skillsDevelopmentEnd);
		setParticipationInSeminars(participationInSeminars);
		setParticipationInSeminarsEnd(participationInSeminarsEnd);
		setOtherJobs(otherJobs);
		setOtherJobsEnd(otherJobsEnd);
		setPlanStatus(planStatus);
		
	}
}

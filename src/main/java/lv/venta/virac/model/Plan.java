package lv.venta.virac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "planTable")
@ToString
@Entity
public class Plan {
	@Id
	@Column(name = "idPlan")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idPlan;
	
	@ManyToOne
	@JoinColumn(name = "idEmployee")
	private Employee employee;
	
	@OneToMany
	@JoinColumn(name = "idYear")
	private Year idYear;
	
	@Column(name = "numOfProjects")
	private int numOfProjects;
	
	@Column(name = "numOfArticles")
	private int numOfArticles;
	
	@Column(name = "partInConf")
	@Size(max = 200, min = 2)
	private String partInConf;
	
	//Q: Vai te var nelikt @NotNull?
	@Column(name = "partInConfEnd")
	@Size(max = 200, min = 2)
	private String partInConfEnd;
	
	@Column(name = "comAbConf")
	@Size(max = 200, min = 2)
	private String comAbConf;
	
	@Column(name = "comAbConfEnd")
	@Size(max = 200, min = 2)
	private String comAbConfEnd;
	
	@Column(name = "numOfCourses")
	private int numOfCourses;
	
	@Column(name = "numOfStudWork")
	@NotNull
	private int numOfStudWork;
	
	@Column(name = "promoOfResearch")
	@Size(max = 200, min = 2)
	private String promoOfResearch;
	
	@Column(name = "promoOfResearchEnd")
	@Size(max = 200, min = 2)
	private String promoOfResearchEnd;
	
	@Column(name = "adminWork")
	@Size(max = 200, min = 2)
	private String adminWork;
	
	@Column(name = "adminWorkEnd")
	@Size(max = 200, min = 2)
	private String adminWorkEnd;
	
	@Column(name = "projApplicSub")
	@Size(max = 200, min = 2)
	private String projApplicSub;
	
	@Column(name = "projApplicSubEnd")
	@Size(max = 200, min = 2)
	private String projApplicSubEnd;
	
	@Column(name = "skillsDevelopment")
	@Size(max = 200, min = 2)
	private String skillsDevelopment;
	
	@Column(name = "skillsDevelopmentEnd")
	@Size(max = 200, min = 2)
	private String skillsDevelopmentEnd;
	
	@Column(name = "participationInSeminars")
	@Size(max = 200, min = 2)
	private String participationInSeminars;
	
	@Column(name = "participationInSeminarsEnd")
	@Size(max = 200, min = 2)
	private String participationInSeminarsEnd;
	
	@Column(name = "otherJobs")
	@Size(max = 200, min = 2)
	private String otherJobs;
	
	@Column(name = "otherJobsEnd")
	@Size(max = 200, min = 2)
	private String otherJobsEnd;
	
	public Plan(Employee employee, Year idYear, int numOfProjects, int numOfArticles, String partInConf, String partInConfEnd,
			String comAbConf, String comAbConfEnd, int numOfCourses, int numOfStudWork, String promoOfResearch, String promoOfResearchEnd,
			String adminWork, String adminWorkEnd, String projApplicSub, String projApplicSubEnd, String skillsDevelopment, 
			String skillsDevelopmentEnd,String participationInSeminars, String participationInSeminarsEnd, String otherJobs, String otherJobsEnd) {
		setEmployee(employee);
		setIdYear(idYear);
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
		
	}
}

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
@Table(name = "planTable")
@ToString
@Entity
public class Plan {

	private int idPlan;
	
	//TODO:connect
	private int idEmployee;
	
	//TODO:connect
	private int idYear;
	
	private int numOfProjects;
	
	private int numOfArticles;
	
	private String partInConf;
	
	private String partInConfEnd;
	
	private String comAbConf;
	
	private String comAbConfEnd;
	
	private int numOfCourses;
	
	private int numOfStudWork;
	
	private String promoOfResearch;
	
	private String promoOfResearchEnd;
	
	private String adminWork;
	
	private String adminWorkEnd;
	
	private String projApplicSub;
	
	private String projApplicSubEnd;
	
	private String skillsDevelopment;
	
	private String skillsDevelopmentEnd;
	
	private String participationInSeminars;
	
	private String participationInSeminarsEnd;
	
	private String otherJobs;
	
	private String otherJobsEnd;
}

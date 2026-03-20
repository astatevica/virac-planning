package lv.venta.virac.dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FullPlanDTO {
	
	private int idPlan;
	private int idEmployee;
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
	
	//No tabulām
	private ArrayList<CoursePlanResponseDTO> courses;
	private ArrayList<ScientificArticlesDTO> articles;
	private ArrayList<ProjectDTO> projects;
	private ArrayList<StudentWorkDTO> studentWork;
	
	//TODO: satusu pievienot

}

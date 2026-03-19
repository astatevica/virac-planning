package lv.venta.virac.dto;

import java.util.ArrayList;

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
	private ArrayList<CourseDTO> courses;
	private ArrayList<ScientificArticlesDTO> articles;
	private ArrayList<ProjectDTO> projects;
	private ArrayList<StudentWorkDTO> studentWork;
	
	//TODO: satusu pievienot
		
	public FullPlanDTO() {
		
	}
	
	public FullPlanDTO(int idPlan, int idEmployee, int idYear, int numOfProjects, int numOfArticles, String partInConf, String partInConfEnd, 
			String comAbConf, String comAbConfEnd, int numOfCourses, int numOfStudWork, String promoOfResearch, String promoOfResearchEnd,
			String adminWork, String adminWorkEnd, String projApplicSub, String projApplicSubEnd, String skillsDevelopment,
			String skillsDevelopmentEnd, String participationInSeminars, String participationInSeminarsEnd, String otherJobs, String otherJobsEnd,
			ArrayList<CourseDTO> courses, ArrayList<ScientificArticlesDTO> articles, ArrayList<ProjectDTO> projects,
			ArrayList<StudentWorkDTO> studentWork) {
		this.setIdPlan(idPlan);
		this.setIdEmployee(idEmployee);
		this.setIdYear(idYear);
		this.setNumOfProjects(numOfProjects);
		this.numOfArticles = numOfArticles;
		this.partInConf = partInConf;
		this.partInConfEnd = partInConfEnd;
		this.comAbConf = comAbConf;
		this.comAbConfEnd = comAbConfEnd;
		this.numOfCourses = numOfCourses;
		this.numOfStudWork = numOfStudWork;
		this.promoOfResearch = promoOfResearch;
		this.promoOfResearchEnd = promoOfResearchEnd;
		this.adminWork = adminWork;
		this.adminWorkEnd = adminWorkEnd;
		this.projApplicSub = projApplicSub;
		this.projApplicSubEnd = projApplicSubEnd;
		this.skillsDevelopment = skillsDevelopment;
		this.skillsDevelopmentEnd = skillsDevelopmentEnd;
		this.participationInSeminars = participationInSeminars;
		this.participationInSeminarsEnd = participationInSeminarsEnd;
		this.otherJobs = otherJobs;
		this.otherJobsEnd = otherJobsEnd;
		this.setCourses(courses);
		this.setArticles(articles);
		this.setProjects(projects);
		this.setStudentWork(studentWork);
	}

	public int getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(int idPlan) {
		this.idPlan = idPlan;
	}

	public int getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(int idEmployee) {
		this.idEmployee = idEmployee;
	}

	public int getIdYear() {
		return idYear;
	}

	public void setIdYear(int idYear) {
		this.idYear = idYear;
	}

	public int getNumOfProjects() {
		return numOfProjects;
	}

	public void setNumOfProjects(int numOfProjects) {
		this.numOfProjects = numOfProjects;
	}
	
	public int getNumOfArticles() {
		return numOfArticles;
	}
	public void setNumOfArticles(int numOfArticles) {
		this.numOfArticles = numOfArticles;
	}
	public String getPartInConf() {
		return partInConf;
	}
	public void setPartInConf(String partInConf) {
		this.partInConf = partInConf;
	}
	public String getPartInConfEnd() {
		return partInConfEnd;
	}
	public void setPartInConfEnd(String partInConfEnd) {
		this.partInConfEnd = partInConfEnd;
	}
	public String getComAbConf() {
		return comAbConf;
	}
	public void setComAbConf(String comAbConf) {
		this.comAbConf = comAbConf;
	}
	public String getComAbConfEnd() {
		return comAbConfEnd;
	}
	public void setComAbConfEnd(String comAbConfEnd) {
		this.comAbConfEnd = comAbConfEnd;
	}
	public int getNumOfCourses() {
		return numOfCourses;
	}
	public void setNumOfCourses(int numOfCourses) {
		this.numOfCourses = numOfCourses;
	}
	public int getNumOfStudWork() {
		return numOfStudWork;
	}
	public void setNumOfStudWork(int numOfStudWork) {
		this.numOfStudWork = numOfStudWork;
	}
	public String getPromoOfResearch() {
		return promoOfResearch;
	}
	public void setPromoOfResearch(String promoOfResearch) {
		this.promoOfResearch = promoOfResearch;
	}
	public String getPromoOfResearchEnd() {
		return promoOfResearchEnd;
	}
	public void setPromoOfResearchEnd(String promoOfResearchEnd) {
		this.promoOfResearchEnd = promoOfResearchEnd;
	}
	public String getAdminWork() {
		return adminWork;
	}
	public void setAdminWork(String adminWork) {
		this.adminWork = adminWork;
	}
	public String getAdminWorkEnd() {
		return adminWorkEnd;
	}
	public void setAdminWorkEnd(String adminWorkEnd) {
		this.adminWorkEnd = adminWorkEnd;
	}
	public String getProjApplicSub() {
		return projApplicSub;
	}
	public void setProjApplicSub(String projApplicSub) {
		this.projApplicSub = projApplicSub;
	}
	public String getProjApplicSubEnd() {
		return projApplicSubEnd;
	}
	public void setProjApplicSubEnd(String projApplicSubEnd) {
		this.projApplicSubEnd = projApplicSubEnd;
	}
	public String getSkillsDevelopment() {
		return skillsDevelopment;
	}
	public void setSkillsDevelopment(String skillsDevelopment) {
		this.skillsDevelopment = skillsDevelopment;
	}
	public String getSkillsDevelopmentEnd() {
		return skillsDevelopmentEnd;
	}
	public void setSkillsDevelopmentEnd(String skillsDevelopmentEnd) {
		this.skillsDevelopmentEnd = skillsDevelopmentEnd;
	}
	public String getParticipationInSeminars() {
		return participationInSeminars;
	}
	public void setParticipationInSeminars(String participationInSeminars) {
		this.participationInSeminars = participationInSeminars;
	}
	public String getParticipationInSeminarsEnd() {
		return participationInSeminarsEnd;
	}
	public void setParticipationInSeminarsEnd(String participationInSeminarsEnd) {
		this.participationInSeminarsEnd = participationInSeminarsEnd;
	}
	public String getOtherJobs() {
		return otherJobs;
	}
	public void setOtherJobs(String otherJobs) {
		this.otherJobs = otherJobs;
	}
	public String getOtherJobsEnd() {
		return otherJobsEnd;
	}
	public void setOtherJobsEnd(String otherJobsEnd) {
		this.otherJobsEnd = otherJobsEnd;
	}

	public ArrayList<CourseDTO> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<CourseDTO> courses) {
		this.courses = courses;
	}

	public ArrayList<ScientificArticlesDTO> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<ScientificArticlesDTO> articles) {
		this.articles = articles;
	}

	public ArrayList<ProjectDTO> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<ProjectDTO> projects) {
		this.projects = projects;
	}

	public ArrayList<StudentWorkDTO> getStudentWork() {
		return studentWork;
	}

	public void setStudentWork(ArrayList<StudentWorkDTO> studentWork) {
		this.studentWork = studentWork;
	}

}

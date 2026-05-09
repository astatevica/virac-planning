package lv.venta.virac;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import lv.venta.virac.model.ArticlePlan;
import lv.venta.virac.model.Course;
import lv.venta.virac.model.CoursePlan;
import lv.venta.virac.model.Employee;
import lv.venta.virac.model.Journal;
import lv.venta.virac.model.Plan;
import lv.venta.virac.model.Project;
import lv.venta.virac.model.ProjectManagement;
import lv.venta.virac.model.ProjectPlan;
import lv.venta.virac.model.ScientificArticles;
import lv.venta.virac.model.StudentWork;
import lv.venta.virac.model.ViracDepartment;
import lv.venta.virac.model.WorkPlan;
import lv.venta.virac.model.Year;
import lv.venta.virac.model.enums.Degree;
import lv.venta.virac.model.enums.PlanStatus;
import lv.venta.virac.repo.IArticlePlanRepo;
import lv.venta.virac.repo.ICoursePlanRepo;
import lv.venta.virac.repo.ICourseRepo;
import lv.venta.virac.repo.IEmployeeRepo;
import lv.venta.virac.repo.IJournalRepo;
import lv.venta.virac.repo.IPlanRepo;
import lv.venta.virac.repo.IProjectManagementRepo;
import lv.venta.virac.repo.IProjectPlanRepo;
import lv.venta.virac.repo.IProjectRepo;
import lv.venta.virac.repo.IScientificArticlesRepo;
import lv.venta.virac.repo.IStudentWorkRepo;
import lv.venta.virac.repo.IViracDepartmentRepo;
import lv.venta.virac.repo.IWorkPlanRepo;
import lv.venta.virac.repo.IYearRepo;
import lv.venta.virac.scheduler.IPlanScheduleRepo;
import lv.venta.virac.scheduler.PlanSchedule;
import lv.venta.virac.user.IUserRepo;
import lv.venta.virac.user.Role;
import lv.venta.virac.user.User;

@EnableJpaAuditing
@SpringBootApplication
public class ViracPlaningIApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViracPlaningIApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner testModelLayer(IArticlePlanRepo artPlanRepo, 
			ICoursePlanRepo courPlanRepo, ICourseRepo courseRepo,
			IEmployeeRepo emploRepo, IYearRepo yearRepo, IJournalRepo jourRepo,
			IPlanRepo planRepo, IProjectManagementRepo projMangRepo, 
			IProjectPlanRepo projPlanRepo, IProjectRepo projRepo,
			IScientificArticlesRepo scientArtRepo, IStudentWorkRepo studWorkRepo,
			IViracDepartmentRepo viracDepRepo, IWorkPlanRepo workPlanRepo, IUserRepo userRepo,
			IPlanScheduleRepo scheduleRepo, PasswordEncoder encoder)
	{
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				
				//YearTable DONE
				Year year = new Year(2022);
				Year year1 = new Year(2023);
				Year year2 = new Year(2024);
				Year year3 = new Year(2025);
				Year year4 = new Year(2026);
				
				yearRepo.saveAll((Arrays.asList(year, year1, year2, year3, year4)));
				
				//Scheduler TABLE
				LocalDate pf = LocalDate.of(2026,2,1);
		        LocalDate df = LocalDate.of(2026,12,25);
				PlanSchedule schedule2026 = new PlanSchedule(year4,pf,df);
				scheduleRepo.save(schedule2026);
		
				//ViracDepartmentTable DONE
				ViracDepartment dep1 = new ViracDepartment("Electronics and Satellite Technology", "Juris", "Uplejs");
				ViracDepartment dep2 = new ViracDepartment("Astronomy and Astrophysics", "Madars", "Zviedrs");
				ViracDepartment dep3 = new ViracDepartment("Engineering & Technical Operations group(ETO/ETOG)", "Zaiga", "Buša");
				ViracDepartment dep4 = new ViracDepartment("Test", "Annija", "Stateviča");
				
				viracDepRepo.saveAll((Arrays.asList(dep1, dep2, dep3, dep4)));
				
				//EmployeeTable DONE
				Employee emp1 = new Employee("Rimants", "Kalniņš", dep1 , "Pētnieks");
				Employee emp2 = new Employee("Juris", "Uplejs", dep1 , "Nodaļas vadītājs, Vadošais pētnieks");
				Employee emp3 = new Employee("Māra", "Dižā", dep1 , "Tehniskais speciālists");
				
				Employee emp4 = new Employee("Anrijs", "Baltacis", dep2 , "Pētnieks");
				Employee emp5 = new Employee("Madars", "Zviedrs", dep2 , "Nodaļas vadītājs, Vadošais pētnieks");
				Employee emp6 = new Employee("Laura", "Akmeņkalna", dep2 , "Tehniskais speciālists");
				
				Employee emp7 = new Employee("Ārija", "Kalvāne", dep3 , "Pētnieks");
				Employee emp8 = new Employee("Zaiga", "Buša", dep3 , "Nodaļas vadītājs, Vadošais pētnieks");
				Employee emp9 = new Employee("Andra", "Puķe", dep3 , "Tehniskais speciālists");
				
				Employee x = new Employee("ADMIN", "TEST", dep4 , "ADMIN test profile");
				Employee y = new Employee("USER", "TEST", dep4 , "USER test profile");
				Employee z = new Employee("DEPARTMENT", "TEST", dep4 , "DEPARTMENT test profile");
				
				emploRepo.saveAll((Arrays.asList(emp1, emp2, emp3, emp4, emp5, emp6, emp7, emp8, emp9, x, y, z)));
				
				//UserTable
				User user1 = User.builder()
						.firstname("Rimants").lastname("Kalniņš").email("rimants.k@venta.lv").
						password(encoder.encode("rimants123")).role(Role.USER).employee(emp1).build();
				User user2 = User.builder()
						.firstname("Juris").lastname("Uplejs").email("juris.u@venta.lv").
						password(encoder.encode("juris123")).role(Role.USER_DEPART).employee(emp2).build();
				User user3 = User.builder()
						.firstname("Māra").lastname("Dižā").email("mara.d@venta.lv").
						password(encoder.encode("mara123")).role(Role.USER).employee(emp3).build();
				
				User user4 = User.builder()
						.firstname("Anrijs").lastname("Baltacis").email("anrijs.b@venta.lv").
						password(encoder.encode("anrijs123")).role(Role.USER).employee(emp4).build();
				User user5 = User.builder()
						.firstname("Madars").lastname("Zviedrs").email("madars.z@venta.lv").
						password(encoder.encode("madars123")).role(Role.USER_DEPART).employee(emp5).build();
				User user6 = User.builder()
						.firstname("Laura").lastname("Akmeņkalna").email("laura.a@venta.lv").
						password(encoder.encode("laura123")).role(Role.USER).employee(emp6).build();
				
				User user7 = User.builder()
						.firstname("Ārija").lastname("Kalvāne").email("arija.k@venta.lv").
						password(encoder.encode("arija123")).role(Role.USER).employee(emp7).build();
				User user8 = User.builder()
						.firstname("Zaiga").lastname("Buša").email("zaiga.b@venta.lv").
						password(encoder.encode("zaiga123")).role(Role.USER_DEPART).employee(emp8).build();
				User user9 = User.builder()
						.firstname("Andra").lastname("Puķe").email("andra.p@venta.lv").
						password(encoder.encode("andra123")).role(Role.USER).employee(emp9).build();
				
				User userX = User.builder()
						.firstname("ADMIN").lastname("ADMIN").email("admin.a@venta.lv").
						password(encoder.encode("admin123")).role(Role.ADMIN).employee(x).build();
				User userY = User.builder()
						.firstname("USER").lastname("USER").email("user.u@venta.lv").
						password(encoder.encode("user123")).role(Role.USER).employee(y).build();
				User userZ = User.builder()
						.firstname("DEPARTMENT").lastname("DEPARTMENT").email("department.d@venta.lv").
						password(encoder.encode("department123")).role(Role.USER_DEPART).employee(z).build();
				
				userRepo.saveAll((Arrays.asList(user1,user2,user3,user4,user5,user6,user7,user8,user9,userX,userY,userZ)));
				
				//PlanTable DONE
				Plan plan1 = new Plan(emp1, year, 1, 1, "Participation in conferences", "Participated in 2 conferences" , "Discussed 2 topics", "Everithyng went well", 2, 2, "Promote 2 reaserch papers", "Did not promote anything", "Meetings witg VeA Board", "Everything went well", null, null, "Attend three courses", null, "Dont have plans", "Participated in Java conference", null, "Planned VIRAC Christmass event",PlanStatus.plan_open);
				Plan plan2 = new Plan(emp5, year4, 1, 1, "Participation in conferences", "Participated in 2 conferences" , "Discussed 2 topics", "Everithyng went well", 2, 2, "Promote 2 reaserch papers", "Did not promote anything", "Meetings witg VeA Board", "Everything went well", null, null, "Attend three courses", null, "Dont have plans", "Participated in Java conference", null, "Planned VIRAC Christmass event",PlanStatus.planned_frozen);
				Plan plan3 = new Plan(emp5, year3, 1, 1, "Participation in conferences", "Participated in 2 conferences" , "Discussed 2 topics", "Everithyng went well", 2, 2, "Promote 2 reaserch papers", "Did not promote anything", "Meetings witg VeA Board", "Everything went well", null, null, "Attend three courses", null, "Dont have plans", "Participated in Java conference", null, "Planned VIRAC Christmass event",PlanStatus.done_frozen);
				Plan plan4 = new Plan(emp5, year2, 1, 1, "Participation in conferences", "Participated in 2 conferences" , "Discussed 2 topics", "Everithyng went well", 2, 2, "Promote 2 reaserch papers", "Did not promote anything", "Meetings witg VeA Board", "Everything went well", null, null, "Attend three courses", null, "Dont have plans", "Participated in Java conference", null, "Planned VIRAC Christmass event",PlanStatus.done_frozen);
				
				// --- EMP1 (Rimants Kalniņš) ---
				Plan p1_1 = new Plan(emp1, year1, 1, 1, "Conf. research", "Done", "Topics discussed", "OK", 1, 1, "Paper promo", "Done", "Board meetings", "OK", null, null, "Course A", "Completed", "No plans", "Java Conf", null, "Event X", PlanStatus.done_frozen);
				Plan p1_2 = new Plan(emp1, year2, 1, 1, "Participation", "Done", "Topics", "OK", 2, 2, "Papers", "In progress", "Board", "OK", null, null, "Course B", null, "Future", "Conf Y", null, "X-mas", PlanStatus.plan_open);
				Plan p1_3 = new Plan(emp1, year3, 1, 1, "Conferences", null, null, null, 2, 0, "Promotion", null, "Board", null, null, null, "Course C", null, "None", null, null, "Events", PlanStatus.plan_open);
				Plan p1_4 = new Plan(
					    emp1,               // idEmployee
					    year4,              // idYear (2026)
					    2,                  // numOfProjects
					    2,                  // numOfArticles
					    "Participation in IEEE International Conference on Software Engineering and AI Research 2026", // partInConf
					    null,               // partInConfEnd (Vēl nav noticis)
					    "Presenting a paper on 'AI-Driven Code Quality Evolution'", // comAbConf
					    null,               // comAbConfEnd
					    1,                  // numOfCourses
					    2,                  // numOfStudWork
					    "Submission of two research papers to 'Journal of Systems and Software'", // promoOfResearch
					    null,               // promoOfResearchEnd
					    "Acting as a lead developer for the Departmental Resource Management System", // adminWork
					    null,               // adminWorkEnd
					    "Submission of a Horizon Europe grant application for sustainable IT solutions", // projApplicSub
					    null,               // projApplicSubEnd
					    "Advanced Deep Learning specialization and certification", // skillsDevelopment
					    null,               // skillsDevelopmentEnd
					    "Monthly internal research seminars on emerging technologies", // participationInSeminars
					    null,               // participationInSeminarsEnd
					    "Co-organizing the University's Annual IT Research Symposium 2026", // otherJobs
					    null,               // otherJobsEnd
					    PlanStatus.planned_frozen // Status
					);
//				int idEmployee, int idYear, int numOfProjects, int numOfArticles, String partInConf, String partInConfEnd, 
//				String comAbConf, String comAbConfEnd, int numOfCourses, int numOfStudWork, String promoOfResearch, String promoOfResearchEnd,
//				String adminWork, String adminWorkEnd, String projApplicSub, String projApplicSubEnd, String skillsDevelopment,
//				String skillsDevelopmentEnd, String participationInSeminars, String participationInSeminarsEnd, String otherJobs, String otherJobsEnd
				
				// --- EMP2 (Juris Uplejs) ---
				Plan p2_1 = new Plan(emp2, year1, 1, 1, "Strategy", "Finished", "Management", "Good", 3, 3, "PR", "Done", "VeA Meeting", "Success", null, null, "Leadership", "Done", "N/A", "Seminars", null, "Ventspils Day", PlanStatus.done_frozen);
				Plan p2_2 = new Plan(emp2, year2, 2, 1, "Academic", "Ongoing", "Planning", "Normal", 1, 1, "Articles", "Submitted", "Board", "OK", null, null, "IT Course", "Active", "N/A", "Webinar", null, "Summer Camp", PlanStatus.plan_open);
				Plan p2_3 = new Plan(emp2, year3, 1, 0, "Research", null, null, null, 1, 0, "Pubs", null, "Board", null, null, null, "Management", null, "N/A", null, null, "New Year", PlanStatus.plan_open);
				Plan p2_4 = new Plan(emp2, year4, 1, 0, "International", null, null, null, 2, 0, "Networking", null, "Board", null, null, null, "English", null, "N/A", null, null, "Reception", PlanStatus.planned_frozen);

				// --- EMP3 (Māra Dižā) ---
				Plan p3_1 = new Plan(emp3, year1, 0, 0, "Tech support", "Fixed all", "Maintenance", "OK", 0, 0, "Hardware", "Updated", "Team", "OK", null, null, "Cisco", "Done", "None", "Support", null, "Inventory", PlanStatus.done_frozen);
				Plan p3_2 = new Plan(emp3, year2, 1, 1, "IT Workshop", "Done", "Tech talk", "Good", 1, 0, "Guidelines", "Draft", "Team", "OK", null, null, "Azure", "In progress", "None", "Azure Day", null, "Clean up", PlanStatus.plan_open);
				Plan p3_3 = new Plan(emp3, year3, 0, 0, "Upgrades", null, null, null, 0, 0, "Documentation", null, "Team", null, null, null, "Security", null, "None", null, null, "Migration", PlanStatus.plan_open);
				Plan p3_4 = new Plan(emp3, year4, 0, 0, "Maintenance", null, null, null, 0, 0, "Monitoring", null, "Team", null, null, null, "Advanced Networking", null, "None", null, null, "Backups", PlanStatus.planned_frozen);

				// --- EMP4 (Anrijs Baltacis) ---
				Plan p4_1 = new Plan(emp4, year1, 1, 1, "Physics Conf", "Presented", "Quantum", "High level", 2, 2, "Journal", "Published", "VeA", "OK", null, null, "Math course", "Done", "N/A", "CERN visit", null, "Expo", PlanStatus.done_frozen);
				Plan p4_2 = new Plan(emp4, year2, 1, 0, "Space research", "Started", "Satellites", "Working", 1, 1, "Book chapter", "Writing", "VeA", "OK", null, null, "Physics", "Active", "N/A", "Workshop", null, "Seminar", PlanStatus.plan_open);
				Plan p4_3 = new Plan(emp4, year3, 1, 1, "EU Projects", null, null, null, 2, 2, "Grant prep", null, "VeA", null, null, null, "Statistics", null, "N/A", null, null, "LIGO event", PlanStatus.plan_open);
				Plan p4_4 = new Plan(emp4, year4, 1, 1, "Global Science", null, null, null, 2, 1, "Cooperation", null, "VeA", null, null, null, "AI for Science", null, "N/A", null, null, "Summit", PlanStatus.planned_frozen);

				// --- EMP6 (Laura Akmeņkalna) ---
				Plan p6_1 = new Plan(emp6, year1, 1, 1, "Database", "Optimized", "SQL", "Speed++", 0, 0, "Manuals", "Done", "Nodaļa", "OK", null, null, "SQL Adv", "Completed", "None", "DB Conf", null, "Pizza party", PlanStatus.done_frozen);
				Plan p6_2 = new Plan(emp6, year2, 1, 0, "Web Dev", "Coding", "React", "Good", 0, 0, "Wiki", "Drafting", "Nodaļa", "OK", null, null, "Frontend", "Learning", "None", "JS Meetup", null, "Workshop", PlanStatus.plan_open);
				Plan p6_3 = new Plan(emp6, year3, 0, 0, "Testing", null, null, null, 0, 0, "QA", null, "Nodaļa", null, null, null, "Automation", null, "None", null, null, "Lab day", PlanStatus.plan_open);
				Plan p6_4 = new Plan(emp6, year4, 1, 1, "Integration", null, null, null, 0, 0, "API", null, "Nodaļa", null, null, null, "Cloud", null, "None", null, null, "Demo day", PlanStatus.planned_frozen);

				/// --- EMP7 (Ārija Kalvāne) ---
				Plan p7_1 = new Plan(emp7, year1, 2, 2, "History", "Archive done", "Ancient", "Detailed", 1, 1, "Exhibition", "Success", "VeA", "OK", null, null, "Archiving", "Done", "Study", "History Day", null, "Heritage", PlanStatus.done_frozen);
				Plan p7_2 = new Plan(emp7, year2, 1, 0, "Digitalization", "Procesā", "Scanners", "OK", 1, 0, "Article", "In progress", "VeA", "OK", null, null, "Digital Hum.", "Active", "Study", "Seminar", null, "Library", PlanStatus.plan_open);
				Plan p7_3 = new Plan(emp7, year3, 1, 1, "Preservation", null, null, null, 1, 1, "Analysis", null, "VeA", null, null, null, "Restoration", null, "Study", null, null, "Archive Day", PlanStatus.plan_open);
				Plan p7_4 = new Plan(emp7, year4, 2, 0, "Modern History", null, null, null, 1, 0, "Book", null, "VeA", null, null, null, "Curation", null, "Study", null, null, "Ceremony", PlanStatus.planned_frozen);

				// --- EMP8 (Zaiga Buša) ---
				Plan p8_1 = new Plan(emp8, year1, 1, 1, "Economics", "Analysis done", "Tirgus", "Precīzi", 2, 2, "Forecasts", "Done", "Board", "Good", null, null, "Finance", "Done", "N/A", "Eco Forum", null, "Gada balle", PlanStatus.done_frozen);
				Plan p8_2 = new Plan(emp8, year2, 1, 1, "Budgeting", "Drafting", "Efficiency", "OK", 1, 1, "Reports", "Monthly", "Board", "OK", null, null, "Excel Mastery", "Ongoing", "N/A", "FinTech", null, "Review", PlanStatus.plan_open);
				Plan p8_3 = new Plan(emp8, year3, 1, 0, "Audit", null, null, null, 1, 0, "Internal", null, "Board", null, null, null, "Management", null, "N/A", null, null, "Plan meeting", PlanStatus.plan_open);
				Plan p8_4 = new Plan(emp8, year4, 2, 2, "Grants", null, null, null, 1, 1, "External", null, "Board", null, null, null, "Sustainability", null, "N/A", null, null, "Audit day", PlanStatus.planned_frozen);

				// --- EMP9 (Andra Puķe) ---
				Plan p9_1 = new Plan(emp9, year1, 0, 0, "Lab Assist", "Inventory", "Tools", "Ready", 0, 0, "Safety", "Checked", "Dept", "OK", null, null, "Safety", "Done", "None", "Health", null, "Cleaning", PlanStatus.done_frozen);
				Plan p9_2 = new Plan(emp9, year2, 1, 1, "Experiment", "Setting up", "Chemistry", "Busy", 0, 0, "Records", "Daily", "Dept", "OK", null, null, "ChemLab", "Active", "None", "Expo", null, "Lab Night", PlanStatus.plan_open);
				Plan p9_3 = new Plan(emp9, year3, 0, 0, "Maintenance", null, null, null, 0, 0, "Devices", null, "Dept", null, null, null, "IT basics", null, "None", null, null, "Move", PlanStatus.plan_open);
				Plan p9_4 = new Plan(emp9, year4, 0, 0, "Supply chain", null, null, null, 0, 0, "Ordering", null, "Dept", null, null, null, "Logistics", null, "None", null, null, "Inventory", PlanStatus.planned_frozen);

				planRepo.saveAll(Arrays.asList(plan1,plan2,plan3,plan4,
						p1_1, p1_2, p1_3, p1_4, p2_1, p2_2, p2_3, p2_4, p3_1, p3_2, p3_3, p3_4, 
				        p4_1, p4_2, p4_3, p4_4, p6_1, p6_2, p6_3, p6_4, p7_1, p7_2, p7_3, p7_4, 
				        p8_1, p8_2, p8_3, p8_4, p9_1, p9_2, p9_3, p9_4));
				
				//CourseTable DONE
				Course c1 = new Course("Software Engineering I", 6, "spring", "ITF");
				Course c2 = new Course("Programming Engineering II", 3, "autumn", "ITF");
				Course c3 = new Course("Databases I", 3, "autumn", "ITF");
				Course c4 = new Course("English for Programmers I", 3, "autumn", "ITF");
				Course c5 = new Course("Fundamentals of Entrepreneurship", 3, "autumn", "ITF");
				Course c6 = new Course("Civil Protection", 1, "autumn", "ITF");
				Course c7 = new Course("Mathematics for Programmers", 12, "autumn", "ITF");
				Course c8 = new Course("Basics of IT Law and Standards", 3, "autumn", "ITF");
				Course c9 = new Course("Sustainable Environmental Development", 3, "autumn", "ITF");
				Course c10 = new Course("Fundamentals of Programming in JAVA", 6, "autumn", "ITF");
				Course c11 = new Course("IT Project Management", 3, "autumn", "ITF");
				Course c12 = new Course("Programming in JAVA", 3, "autumn", "ITF");
				Course c13 = new Course("Web Application Development", 6, "spring", "ITF");
				Course c14 = new Course("Software Testing and Automation", 3, "spring", "ITF");
				Course c15 = new Course("Data Structures and Algorithms", 3, "spring", "ITF");
				Course c16 = new Course("Databases II", 3, "spring", "ITF");
				
				courseRepo.saveAll((Arrays.asList(c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16)));
				
				//CoursePlanTable DONE
				CoursePlan cp1 = new CoursePlan(plan1, c3, "Handout materials need to be updated");
				CoursePlan cp2 = new CoursePlan(plan1, c1, "Average grade: 8.5/10");
				CoursePlan cp3 = new CoursePlan(plan1, c2, "Successfully conducted. All students passed the final exam.");
				CoursePlan cp4 = new CoursePlan(plan2, c2, "Successfully conducted. Excellent student engagement.");
				
				// --- Rimants Kalniņš (emp1) kursi ---
				CoursePlan cp5 = new CoursePlan(p1_1, c1, "Successfully conducted. Average grade: 9.2");
				CoursePlan cp6 = new CoursePlan(p1_2, c10, "In progress: students are learning OOP fundamentals");
				CoursePlan cp7 = new CoursePlan(p1_3, c12, "Planned: new IntelliJ license required");
				CoursePlan cp8 = new CoursePlan(p1_4, c15, "In preparation");

				// --- Juris Uplejs (emp2) kursi ---
				CoursePlan cp9 = new CoursePlan(p2_1, c11, "Pabeigts: izcili rezultāti projektu prezentācijās");
				CoursePlan cp10 = new CoursePlan(p2_2, c8, "Lekcijas notiek tiešsaistē");
				CoursePlan cp11 = new CoursePlan(p2_3, c11, "Plānots piesaistīt vieslektoru no industrijas");
				CoursePlan cp12 = new CoursePlan(p2_4, c5, "Nepieciešams atjaunot mācību literatūru");

				// --- Māra Dižā (emp3) kursi ---
				CoursePlan cp13 = new CoursePlan(p3_1, c3, "Visiem studentiem veiksmīga laboratorijas darbu nodeve");
				CoursePlan cp14 = new CoursePlan(p3_2, c16, "Sagatavoti jauni SQL uzdevumi");
				CoursePlan cp15 = new CoursePlan(p3_3, c3, "Gaidām telpas apstiprinājumu");
				CoursePlan cp16 = new CoursePlan(p3_4, c16, "Plānots pāriet uz PostgreSQL");

				// --- Anrijs Baltacis (emp4) kursi ---
				CoursePlan cp17 = new CoursePlan(p4_1, c7, "Sarežģīts kurss, nepieciešamas papildu konsultācijas");
				CoursePlan cp18 = new CoursePlan(p4_2, c7, "Gatavošanās eksāmenam");
				CoursePlan cp19 = new CoursePlan(p4_3, c4, "Uzsvars uz zinātnisko rakstu tulkošanu");
				CoursePlan cp20 = new CoursePlan(p4_4, c4, "Plānots: tehniskās dokumentācijas modulis");

				// --- Laura Akmeņkalna (emp6) kursi ---
				CoursePlan cp21 = new CoursePlan(p6_1, c13, "Izstrādātas 15 jaunas tīmekļa lietotnes");
				CoursePlan cp22 = new CoursePlan(p6_2, c13, "Studenti strādā pie React projektiem");
				CoursePlan cp23 = new CoursePlan(p6_3, c14, "Plānots ieviest Selenium testēšanu");
				CoursePlan cp24 = new CoursePlan(p6_4, c14, "Sagatavošanā: Jenkins integrācijas piemēri");

				// --- Ārija Kalvāne (emp7) kursi ---
				CoursePlan cp25 = new CoursePlan(p7_1, c9, "Veiksmīgi novadītas lauka studijas");
				CoursePlan cp26 = new CoursePlan(p7_2, c9, "Diskusijas par klimata neitralitāti");
				CoursePlan cp27 = new CoursePlan(p7_3, c6, "Nepieciešams praktiskais ekipējums");
				CoursePlan cp28 = new CoursePlan(p7_4, c6, "Plānots piesaistīt VUGD speciālistus");

				// --- Zaiga Buša (emp8) kursi ---
				CoursePlan cp29 = new CoursePlan(p8_1, c5, "Augsta studentu aktivitāte semināros");
				CoursePlan cp30 = new CoursePlan(p8_2, c5, "Biznesa plānu izstrādes stadija");
				CoursePlan cp31 = new CoursePlan(p8_3, c8, "Plānots: GDPR modulis");
				CoursePlan cp32 = new CoursePlan(p8_4, c8, "Aktuālo tiesību aktu apskats");

				// --- Andra Puķe (emp9) kursi ---
				CoursePlan cp33 = new CoursePlan(p9_1, c6, "Instruktāža veikta sekmīgi");
				CoursePlan cp34 = new CoursePlan(p9_2, c3, "Laboratorijas iekārtu demonstrācija");
				CoursePlan cp35 = new CoursePlan(p9_3, c16, "Plānots: datu bāzu drošības aspekti");
				CoursePlan cp36 = new CoursePlan(p9_4, c4, "Tehniskās terminoloģijas tests");

				courPlanRepo.saveAll(Arrays.asList(
					cp1,cp2,cp3,cp4,cp5, cp6, cp7, cp8, cp9, cp10, cp11, cp12, cp13, cp14, cp15, cp16,
				    cp17, cp18, cp19, cp20, cp21, cp22, cp23, cp24, cp25, cp26, cp27, cp28,
				    cp29, cp30, cp31, cp32, cp33, cp34, cp35, cp36
				));
				
				//JournalTable DONE
				Journal journ1 = new Journal("IET Software");
				Journal journ2 = new Journal("Empirical Software Engineering");
				Journal journ3 = new Journal("ACM Computing Surveys");
				Journal journ4 = new Journal("Communications of the ACM");
				Journal journ5 = new Journal("IEEE Transactions on Pattern Analysis and Machine Intelligence");
				Journal journ6 = new Journal("Management Information Systems Quarterly");
				
				jourRepo.saveAll((Arrays.asList(journ1,journ2,journ3,journ4,journ5,journ6)));
				
				//ScientificArticles DONE 
				ScientificArticles scArt1 = new ScientificArticles("Impact of Co-Occurrences "
						+ "of Code Smells and Design Patterns on Internal Code Quality Attributes",
						"Sania Imran, Irum Inayat, Maya Daneva", journ1);
				
				ScientificArticles scArt2 = new ScientificArticles("On detection latencies "
						+ "of network intrusion detectors – discussion and application", 
						"Tommaso Puccetti & Andrea Ceccarelli ", journ2); 

				ScientificArticles scArt3 = new ScientificArticles(
				    "Deep Learning-Based Automated Program Repair: An Empirical Study",
				    "Kui Liu, Shangwen Wang, Tegawendé F. Bissyandé", 
				    journ2);

				ScientificArticles scArt4 = new ScientificArticles(
				    "On the Relationship Between Technical Debt and Software Maintainability",
				    "Zadia Silva, Alfredo Goldman, Fabio Kon", 
				    journ4);

				ScientificArticles scArt5 = new ScientificArticles(
				    "Automated Unit Test Generation for Python: An Industrial Experience Report",
				    "Mitchell Olsthoorn, Arie van Deursen, Annibale Panichella", 
				    journ4);

				ScientificArticles scArt6 = new ScientificArticles(
				    "The Impact of Test-Driven Development on Software Quality and Development Time",
				    "Burak Turhan, Ayşe Bener, Melih Demirörs", 
				    journ1);
				
				ScientificArticles scArt7 = new ScientificArticles(
					    "A Comprehensive Study of Code Smells in Front-End Web Development",
					    "Davide Di Ruscio, Paola Inverardi, Patrizio Pelliccione", 
					    journ1);

				scientArtRepo.saveAll((Arrays.asList(scArt1,scArt2,scArt3,scArt4,scArt5,scArt6,scArt7)));
				
				//ArticlePlanTable DONE
				ArticlePlan artPlan1 = new ArticlePlan(plan1, scArt2, "Development completed in late 2024", "https://arxiv.org/abs/2402.09082");
				ArticlePlan artPlan2 = new ArticlePlan(plan1, scArt1, "Development completed in early 2024", "https://doi.org/10.1049/sfw2/5579438");
				ArticlePlan artPlan3 = new ArticlePlan(plan1, scArt3, "Published and peer-reviewed in March 2025", "https://doi.org/10.1145/3377811.3380338");
				ArticlePlan artPlan4 = new ArticlePlan(plan2, scArt4, "Status: Awaiting reviewer response", "https://arxiv.org/abs/2103.10111");
				ArticlePlan artPlan5 = new ArticlePlan(plan2, scArt5, "Development discontinued due to lack of data", "https://doi.org/10.1109/ICSE.2019.00031");
				ArticlePlan artPlan6 = new ArticlePlan(plan3, scArt6, "Prepared for conference submission", "https://arxiv.org/abs/2301.04567");
				ArticlePlan artPlan7 = new ArticlePlan(plan1, scArt2, "Additional analysis required before publication", "https://doi.org/10.1007/s11219-023-09612-z");

				// --- EMP1 (Rimants Kalniņš) ---
				ArticlePlan ap1_1 = new ArticlePlan(p1_1, scArt1, "Published: Journal of Systems and Software", "https://doi.org/10.1016/j.jss.2023.01");
				ArticlePlan ap1_2 = new ArticlePlan(p1_2, scArt4, "Data collection completed; first draft created", "https://arxiv.org/abs/2401.12345");
				ArticlePlan ap1_3 = new ArticlePlan(p1_3, scArt7, "Status: Planning article structure", null);
				ArticlePlan ap1_4 = new ArticlePlan(p1_4, scArt1, "Planned to conduct a repeat study in a new environment", null);

				// --- EMP2 (Juris Uplejs) ---
				ArticlePlan ap2_1 = new ArticlePlan(p2_1, scArt2, "Publicēts un prezentēts konferencē", "https://doi.org/10.1007/s11219-023-1");
				ArticlePlan ap2_2 = new ArticlePlan(p2_2, scArt3, "Gaida recenzentu atsauksmes", "https://arxiv.org/abs/2405.09876");
				ArticlePlan ap2_3 = new ArticlePlan(p2_3, scArt6, "Sagatavošanā: literatūras apskata sadaļa", null);
				ArticlePlan ap2_4 = new ArticlePlan(p2_4, scArt2, "Plānots papildināt ar tīkla analīzes datiem", null);

				// --- EMP3 (Māra Dižā) ---
				ArticlePlan ap3_1 = new ArticlePlan(p3_1, scArt5, "Izstrāde pabeigta, iekšējā recenzija sekmīga", "https://doi.org/10.1109/ICSE.2023.05");
				ArticlePlan ap3_2 = new ArticlePlan(p3_2, scArt5, "Papildināts ar jauniem Python testu rīkiem", "https://arxiv.org/abs/2403.55443");
				ArticlePlan ap3_3 = new ArticlePlan(p3_3, scArt4, "Datu analīzes fāze", null);
				ArticlePlan ap3_4 = new ArticlePlan(p3_4, scArt5, "Plānota automatizācijas rīku salīdzināšana", null);

				// --- EMP4 (Anrijs Baltacis) ---
				ArticlePlan ap4_1 = new ArticlePlan(p4_1, scArt3, "Publicēts: IEEE Transactions", "https://doi.org/10.1109/TPAMI.2023.09");
				ArticlePlan ap4_2 = new ArticlePlan(p4_2, scArt6, "Iesniegts izvērtēšanai", "https://arxiv.org/abs/2402.11223");
				ArticlePlan ap4_3 = new ArticlePlan(p4_3, scArt7, "Eksperimentu veikšana uz front-end ietvariem", null);
				ArticlePlan ap4_4 = new ArticlePlan(p4_4, scArt3, "Pētījuma metodoloģijas izstrāde", null);

				// --- EMP6 (Laura Akmeņkalna) ---
				ArticlePlan ap6_1 = new ArticlePlan(p6_1, scArt7, "Pabeigta koda smaržu analīze", "https://doi.org/10.1145/3377811");
				ArticlePlan ap6_2 = new ArticlePlan(p6_2, scArt1, "Papildināta diskusiju sadaļa", "https://arxiv.org/abs/2406.00112");
				ArticlePlan ap6_3 = new ArticlePlan(p6_3, scArt5, "Sagatavots abstrakts", null);
				ArticlePlan ap6_4 = new ArticlePlan(p6_4, scArt7, "Plānots pētījums par React komponentēm", null);

				// --- EMP7 (Ārija Kalvāne) ---
				ArticlePlan ap7_1 = new ArticlePlan(p7_1, scArt2, "Publicēts: Network Security Journal", "https://doi.org/10.1049/sfw2.123");
				ArticlePlan ap7_2 = new ArticlePlan(p7_2, scArt4, "Vākti dati par tehnisko parādu", "https://arxiv.org/abs/2408.99887");
				ArticlePlan ap7_3 = new ArticlePlan(p7_3, scArt6, "Tiek izstrādāts pētījuma prototips", null);
				ArticlePlan ap7_4 = new ArticlePlan(p7_4, scArt2, "Plānots izpētīt jaunākos ielaušanās detektorus", null);

				// --- EMP8 (Zaiga Buša) ---
				ArticlePlan ap8_1 = new ArticlePlan(p8_1, scArt4, "Publicēts un pieejams repozitorijā", "https://doi.org/10.1007/s11219");
				ArticlePlan ap8_2 = new ArticlePlan(p8_2, scArt1, "Labojumi pēc recenzentu ieteikumiem", "https://arxiv.org/abs/2409.77665");
				ArticlePlan ap8_3 = new ArticlePlan(p8_3, scArt3, "Algoritmu veiktspējas salīdzināšana", null);
				ArticlePlan ap8_4 = new ArticlePlan(p8_4, scArt4, "Plānota interviju sērija ar vadītājiem", null);

				// --- EMP9 (Andra Puķe) ---
				ArticlePlan ap9_1 = new ArticlePlan(p9_1, scArt6, "Publicēts: Software Quality Journal", "https://doi.org/10.1145/009876");
				ArticlePlan ap9_2 = new ArticlePlan(p9_2, scArt5, "Analizētas industriālās atsauksmes", "https://arxiv.org/abs/2410.11221");
				ArticlePlan ap9_3 = new ArticlePlan(p9_3, scArt2, "Literatūras avotu atlase", null);
				ArticlePlan ap9_4 = new ArticlePlan(p9_4, scArt6, "Plānota TDD ietekmes analīze jaunā projektā", null);

				// Saglabājam visus ArticlePlan objektus
				artPlanRepo.saveAll(Arrays.asList(
					artPlan1,artPlan2,artPlan3,artPlan4,artPlan5,artPlan6,artPlan7,
				    ap1_1, ap1_2, ap1_3, ap1_4, ap2_1, ap2_2, ap2_3, ap2_4,
				    ap3_1, ap3_2, ap3_3, ap3_4, ap4_1, ap4_2, ap4_3, ap4_4,
				    ap6_1, ap6_2, ap6_3, ap6_4, ap7_1, ap7_2, ap7_3, ap7_4,
				    ap8_1, ap8_2, ap8_3, ap8_4, ap9_1, ap9_2, ap9_3, ap9_4
				));
				
				//StudentWorkTable DONE
				StudentWork stw1 = new StudentWork("Development of the VIRAC Personnel Planning System", "Annija", "Stateviča", Degree.pirma_cikla);
				StudentWork stw2 = new StudentWork("Design and Prototype Development of a Client Registration and Automated Notification Module for Gaisabalons.lv CRM", "Raivis", "Dzenis", Degree.bakalaurs);
				StudentWork stw3 = new StudentWork("Development of a Smart Home Energy Efficiency Monitoring System", "Kārlis", "Bērziņš", Degree.doktors);
				StudentWork stw4 = new StudentWork("Digitalization of Warehouse Inventory Processes and Mobile App Prototyping for SIA 'Logi24'", "Marta", "Zariņa", Degree.pirma_cikla);
				StudentWork stw5 = new StudentWork("Comparative Analysis of Data Encryption Algorithms and Their Implementation in Python", "Jānis", "Ozols", Degree.bakalaurs);
				StudentWork stw6 = new StudentWork("AI Chatbot Integration for Customer Support Automation in E-commerce Platforms", "Elīna", "Kalniņa", Degree.magistrs);
				StudentWork stw7 = new StudentWork("Development of a Visualization Tool for Local Government Public Procurement Using React and D3.js", "Artūrs", "Vītols", Degree.pirma_cikla);
				StudentWork stw8 = new StudentWork("Development of a Blockchain-Based Voting System Prototype for Student Council Elections", "Laura", "Priede", Degree.cits);
				StudentWork stw9 = new StudentWork("Using Machine Learning Models for Automated Code Quality Assessment in CI/CD Pipelines", "Dāvis", "Siliņš", Degree.doktors);
		
				studWorkRepo.saveAll(Arrays.asList(stw1,stw2,stw3,stw4,stw5,stw6,stw7,stw8,stw9));
				
				//WorkPlanTable DONE
				WorkPlan wp1 = new WorkPlan(stw1, plan1, "Development stage: 90%");
				WorkPlan wp2 = new WorkPlan(stw2, plan1, "Defended with a grade of 9");
				WorkPlan wp3 = new WorkPlan(stw3, plan1, "Thesis completed, awaiting defense");
				WorkPlan wp4 = new WorkPlan(stw4, plan2, "Defended with a grade of 8");
				WorkPlan wp5 = new WorkPlan(stw5, plan2, "Corrections required in the practical section");
				WorkPlan wp6 = new WorkPlan(stw6, plan1, "Defended with a grade of 10 (Excellent)");
				WorkPlan wp7 = new WorkPlan(stw7, plan3, "Discontinued at the student's request");
				WorkPlan wp8 = new WorkPlan(stw8, plan1, "Reviewer's evaluation: 7 points");
				WorkPlan wp9 = new WorkPlan(stw9, plan2, "Development stage: 50%, deadlines delayed");

				// --- EMP1 (Rimants Kalniņš - Researcher) ---
				WorkPlan wp10 = new WorkPlan(stw1, p1_1, "Successfully completed and system commissioned");
				WorkPlan wp11 = new WorkPlan(stw2, p1_2, "Defended with distinction");

				// --- EMP2 (Juris Uplejs - Nodaļas vadītājs, Vadošais pētnieks)
				WorkPlan wp12 = new WorkPlan(stw3, p2_1, "Doktora darbs pabeigts; publikācijas sagatavotas");
				WorkPlan wp13 = new WorkPlan(stw6, p2_2, "Maģistra darba izstrādes procesā; tiek vākti dati");
				WorkPlan wp14 = new WorkPlan(stw9, p2_3, "Doktora darba recenzēšanas fāze");

				// --- EMP4 (Anrijs Baltacis - Pētnieks) ---
				WorkPlan wp15 = new WorkPlan(stw4, p4_1, "Bakalaura darbs aizstāvēts uz 8 ballēm");
				WorkPlan wp16 = new WorkPlan(stw5, p4_2, "Notiek algoritmu testēšana Python vidē");

				// --- EMP5 (Madars Zviedrs - Nodaļas vadītājs) ---
				WorkPlan wp17 = new WorkPlan(stw7, plan1, "Aizstāvēts uz 9 ballēm");
				WorkPlan wp18 = new WorkPlan(stw8, plan2, "Darbs pārtraukts akadēmiskā atvaļinājuma dēļ");

				// --- EMP8 (Zaiga Buša - Nodaļas vadītāja) ---
				WorkPlan wp19 = new WorkPlan(stw1, p8_2, "Prakses ietvaros izstrādāts papildmodulis");
				WorkPlan wp20 = new WorkPlan(stw6, p8_3, "Nepieciešami būtiski uzlabojumi nodaļā 'Rezultāti'");

				workPlanRepo.saveAll(Arrays.asList(
						wp1,wp2,wp3, wp4, wp5, wp6, wp7, wp8, wp9, wp10, wp11, wp12, wp13, wp14, wp15, wp16, wp17, wp18, wp19, wp20
				));

				
				//ProjectManagementTable DONE
		        LocalDate sd1 = LocalDate.of(2024,03,28);
		        LocalDate ed1 = LocalDate.of(2025,10,3);
		        LocalDate sd2 = LocalDate.of(2024,01,6);
		        LocalDate ed2 = LocalDate.of(2025,11,13);
		        LocalDate sd3 = LocalDate.of(2023,10,1);
		        LocalDate ed3 = LocalDate.of(2024,06,30);
		        LocalDate sd4 = LocalDate.of(2024,05,15);
		        LocalDate ed4 = LocalDate.of(2026,05,15);
		        LocalDate sd5 = LocalDate.of(2025,01,10);
		        LocalDate ed5 = LocalDate.of(2025,12,20);
		        LocalDate sd6 = LocalDate.of(2024,02,01);
		        LocalDate ed6 = LocalDate.of(2024,10,01);
				
				ProjectManagement projMan1 = new ProjectManagement(emp1, sd1, ed1);
				ProjectManagement projMan2 = new ProjectManagement(emp2, sd1, ed1);
				ProjectManagement projMan3 = new ProjectManagement(emp3, sd3, ed3); // Noslēgts projekts
				ProjectManagement projMan4 = new ProjectManagement(emp4, sd4, ed4); // Aktīvs, garš termiņš
				ProjectManagement projMan5 = new ProjectManagement(emp5, sd5, ed5); // Plānots projekts
				ProjectManagement projMan6 = new ProjectManagement(emp1, sd6, ed6); // Tas pats darbinieks (emp1), cits projekts
				ProjectManagement projMan7 = new ProjectManagement(emp2, sd4, ed5); // Pārklājošies datumi ar projMan2
				ProjectManagement projMan8 = new ProjectManagement(emp6, sd3, ed4); // Vecs sākums, tāls beigu termiņš
				ProjectManagement projMan9 = new ProjectManagement(emp3, sd6, ed5); // Darbinieka rotācija starp projektiem
				
				projMangRepo.saveAll(Arrays.asList(projMan1,projMan2,projMan3, projMan4, projMan5, projMan6, projMan7, projMan8, projMan9));
				
				//ProjectTable DONE
				Project proj1 = new Project("Project 1", 54862, projMan1, sd1, ed1, "P1");
				Project proj2 = new Project("Project 2", 43512, projMan2, sd2, ed2, "P2");
				Project proj3 = new Project("Viedās pilsētas sensorsistēma", 125000, projMan3, sd3, ed3, "VSS-2023");
				Project proj4 = new Project("Mākoņpakalpojumu migrācija", 85400, projMan4, sd4, ed4, "CLOUD-EXP");
				Project proj5 = new Project("Iekšējās drošības audits", 12000, projMan5, sd5, ed5, "SEC-AUD");
				Project proj6 = new Project("Lielo datu analītikas rīks", 250600, projMan6, sd6, ed6, "BIGDATA-01");
				Project proj7 = new Project("Lietotāju pieredzes (UX) izpēte", 5500, projMan7, sd4, ed5, "UX-LAB");
				Project proj8 = new Project("E-paraksta integrācijas modulis", 45000, projMan8, sd3, ed4, "E-SIG-INT");
				Project proj9 = new Project("Mašīnmācīšanās modeļu apmācība", 198000, projMan9, sd6, ed5, "ML-TRAIN");
				
				projRepo.saveAll(Arrays.asList(proj1,proj2,proj3, proj4, proj5, proj6, proj7, proj8, proj9));
				
				//ProjectPlanTable
				ProjectPlan projPlan1 = new ProjectPlan(plan1, proj1, "Task1, Task 2", null);
				ProjectPlan projPlan2 = new ProjectPlan(plan1, proj2, null, "Done");
				ProjectPlan projPlan3 = new ProjectPlan(plan3, proj2, null, "Done");
				ProjectPlan projPlan4 = new ProjectPlan(plan4, proj1, null, "Done");
				ProjectPlan projPlan5 = new ProjectPlan(plan4, proj2, null, "Done");
				ProjectPlan projPlan6 = new ProjectPlan(plan1, proj3, "System architecture design, data model definition", "In Progress");
				ProjectPlan projPlan7 = new ProjectPlan(plan2, proj4, "Server configuration, data migration scripts", "Almost finished");
				ProjectPlan projPlan8 = new ProjectPlan(plan2, proj5, "Security protocol verification", "Completed");
				ProjectPlan projPlan9 = new ProjectPlan(plan3, proj6, "Big data cluster setup", "Delayed: awaiting hardware");
				ProjectPlan projPlan10 = new ProjectPlan(plan3, proj7, "Conducting user interviews", "Done");
				ProjectPlan projPlan11 = new ProjectPlan(plan4, proj8, "API documentation preparation", "Planned");
				ProjectPlan projPlan12 = new ProjectPlan(plan4, proj9, "Model training on test data", "Testing phase");

				// --- EMP1 (Rimants Kalniņš) ---
				ProjectPlan pp1_1 = new ProjectPlan(p1_1, proj3, "Development of sensor data processing algorithms", "Done");
				ProjectPlan pp1_2 = new ProjectPlan(p1_2, proj6, "Data visualization module integration", "In Progress");
				ProjectPlan pp1_3 = new ProjectPlan(p1_3, proj9, "Model validation on real-world data", "Planned");
				ProjectPlan pp1_4 = new ProjectPlan(p1_4, proj3, "System scalability testing", "Reserved");

				// --- EMP2 (Juris Uplejs) ---
				ProjectPlan pp2_1 = new ProjectPlan(p2_1, proj1, "Projekta dokumentācijas vadība", "Done");
				ProjectPlan pp2_2 = new ProjectPlan(p2_2, proj4, "Migrācijas risku analīze", "Procesā");
				ProjectPlan pp2_3 = new ProjectPlan(p2_3, proj8, "Drošības sertifikātu pārbaude", "Gatavošanā");
				ProjectPlan pp2_4 = new ProjectPlan(p2_4, proj6, "Analītikas rīka paplašināšana", "Plānots");

				// --- EMP3 (Māra Dižā) ---
				ProjectPlan pp3_1 = new ProjectPlan(p3_1, proj2, "Klientu datu bāzes migrācija", "Done");
				ProjectPlan pp3_2 = new ProjectPlan(p3_2, proj4, "Mākoņa infrastruktūras uzturēšana", "Aktīvs");
				ProjectPlan pp3_3 = new ProjectPlan(p3_3, proj8, "API galapunktu konfigurēšana", "Plānots");
				ProjectPlan pp3_4 = new ProjectPlan(p3_4, proj5, "Sistēmas ielaušanās testi", "Sagatavošanā");

				// --- EMP4 (Anrijs Baltacis) ---
				ProjectPlan pp4_1 = new ProjectPlan(p4_1, proj3, "Fizikālo mērījumu matemātiskā modelēšana", "Pabeigts");
				ProjectPlan pp4_2 = new ProjectPlan(p4_2, proj9, "Neironu tīklu arhitektūras izvēle", "Procesā");
				ProjectPlan pp4_3 = new ProjectPlan(p4_3, proj1, "Pētniecības atskaišu sagatavošana", "Aktīvs");
				ProjectPlan pp4_4 = new ProjectPlan(p4_4, proj6, "Datu kopu tīrīšana un sagatavošana", "Plānots");

				// --- EMP6 (Laura Akmeņkalna) ---
				ProjectPlan pp6_1 = new ProjectPlan(p6_1, proj7, "Lietotāju plūsmas diagrammu izstrāde", "Done");
				ProjectPlan pp6_2 = new ProjectPlan(p6_2, proj7, "Prototipa testēšana ar fokusa grupām", "In Progress");
				ProjectPlan pp6_3 = new ProjectPlan(p6_3, proj2, "Frontend komponentu audits", "Gaidīšanas režīmā");
				ProjectPlan pp6_4 = new ProjectPlan(p6_4, proj4, "Interfeisa pielāgošana mākoņa videi", "Plānots");

				// --- EMP7 (Ārija Kalvāne) ---
				ProjectPlan pp7_1 = new ProjectPlan(p7_1, proj5, "Vēsturisko datu drošības protokoli", "Done");
				ProjectPlan pp7_2 = new ProjectPlan(p7_2, proj8, "Parakstu autentifikācijas procesa izpēte", "Aktīvs");
				ProjectPlan pp7_3 = new ProjectPlan(p7_3, proj3, "Vides monitoringa datu arhivēšana", "Plānots");
				ProjectPlan pp7_4 = new ProjectPlan(p7_4, proj7, "Digitālā arhīva pieejamības tests", "Sagatavošanā");

				// --- EMP8 (Zaiga Buša) ---
				ProjectPlan pp8_1 = new ProjectPlan(p8_1, proj2, "Budžeta plānošanas moduļa audits", "Done");
				ProjectPlan pp8_2 = new ProjectPlan(p8_2, proj1, "Finanšu atskaišu automatizācija", "Procesā");
				ProjectPlan pp8_3 = new ProjectPlan(p8_3, proj5, "Atbilstības pārbaude jauniem standartiem", "Gaidām apstiprinājumu");
				ProjectPlan pp8_4 = new ProjectPlan(p8_4, proj9, "Ekonomisko modeļu prognozēšana", "Plānots");

				// --- EMP9 (Andra Puķe) ---
				ProjectPlan pp9_1 = new ProjectPlan(p9_1, proj3, "Laboratorijas sensoru kalibrēšana", "Done");
				ProjectPlan pp9_2 = new ProjectPlan(p9_2, proj6, "Datu ievades kļūdu kontroles rīks", "Aktīvs");
				ProjectPlan pp9_3 = new ProjectPlan(p9_3, proj2, "Tehniskās apkopes grafika izstrāde", "Plānots");
				ProjectPlan pp9_4 = new ProjectPlan(p9_4, proj8, "Integrācijas moduļa stresa testi", "Sagatavošanā");

				// Saglabājam visus ProjectPlan objektus
				projPlanRepo.saveAll(Arrays.asList(
				    pp1_1, pp1_2, pp1_3, pp1_4, pp2_1, pp2_2, pp2_3, pp2_4,
				    pp3_1, pp3_2, pp3_3, pp3_4, pp4_1, pp4_2, pp4_3, pp4_4,
				    pp6_1, pp6_2, pp6_3, pp6_4, pp7_1, pp7_2, pp7_3, pp7_4,
				    pp8_1, pp8_2, pp8_3, pp8_4, pp9_1, pp9_2, pp9_3, pp9_4
				));
				
				projPlanRepo.saveAll(Arrays.asList(projPlan1,projPlan2,projPlan3,projPlan4, projPlan5,
						projPlan6, projPlan7, projPlan8, projPlan9, projPlan10, projPlan11, projPlan12
					));
				
			}
		};
	}

}

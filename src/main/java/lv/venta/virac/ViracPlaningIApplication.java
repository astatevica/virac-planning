package lv.venta.virac;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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
import lv.venta.virac.user.IUserRepo;
import lv.venta.virac.user.Role;
import lv.venta.virac.user.User;

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
			PasswordEncoder encoder)
	{
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				
				//YearTable DONE
				Year year1 = new Year(2023);
				Year year2 = new Year(2024);
				Year year3 = new Year(2025);
				Year year4 = new Year(2026);
				
				yearRepo.saveAll((Arrays.asList(year1, year2, year3, year4)));
		
				//ViracDepartmentTable DONE
				ViracDepartment dep1 = new ViracDepartment("Electronics and Satellite Technology", "Karina", "Šķirmante");
				ViracDepartment dep2 = new ViracDepartment("Astronomy and Astrophysics", "Juris", "Kalvāns");
				ViracDepartment dep3 = new ViracDepartment("Engineering & Technical Operations group(ETO/ETOG)", "Juris", "Freimanis");
				ViracDepartment dep4 = new ViracDepartment("Test", "Test", "Test");
				
				viracDepRepo.saveAll((Arrays.asList(dep1, dep2, dep3, dep4)));
				
				//EmployeeTable DONE
				Employee emp1 = new Employee("Karina", "Šķirmante", dep1 , "Pētnieks");
				Employee emp2 = new Employee("Juris", "Kalvāns", dep2 , "Nodaļas vadītājs, Vadošais pētnieks");
				Employee emp3 = new Employee("Māra", "Klausa", dep3 , "Tehniskais speciālists");
				Employee emp4 = new Employee("ADMIN", "TEST", dep4 , "ADMIN test profile");
				Employee emp5 = new Employee("USER", "TEST", dep4 , "USER test profile");
				
				emploRepo.saveAll((Arrays.asList(emp1, emp2, emp3, emp4, emp5)));
				
				//UserTable
				User user1 = User.builder()
						.firstname("Karina").lastname("Šķirmante").email("karina@venta.lv").
						password(encoder.encode("skirmante123")).role(Role.USER).employee(emp1).build();
				User user2 = User.builder()
						.firstname("Juris").lastname("Kalvāns").email("kalvans@venta.lv").
						password(encoder.encode("kalvans123")).role(Role.USER).employee(emp2).build();
				User user3 = User.builder()
						.firstname("Annija").lastname("Developer").email("dev@venta.lv").
						password(encoder.encode("dev123")).role(Role.ADMIN).employee(emp3).build();
				
				User user4 = User.builder()
						.firstname("ADMIN").lastname("TEST").email("admin@venta.lv").
						password(encoder.encode("admin123")).role(Role.ADMIN).employee(emp4).build();
				
				User user5 = User.builder()
						.firstname("USER").lastname("TEST").email("user@venta.lv").
						password(encoder.encode("user123")).role(Role.USER).employee(emp5).build();
				
				userRepo.saveAll((Arrays.asList(user1,user2,user3,user4,user5)));
				
				//PlanTable DONE
				Plan plan1 = new Plan(emp1, year2, 1, 1, "Participation in conferences", "Participated in 2 conferences" , "Discussed 2 topics", "Everithyng went well", 2, 2, "Promote 2 reaserch papers", "Did not promote anything", "Meetings witg VeA Board", "Everything went well", null, null, "Attend three courses", null, "Dont have plans", "Participated in Java conference", null, "Planned VIRAC Christmass event",PlanStatus.plan_open);
				Plan plan2 = new Plan(emp5, year4, 1, 1, "Participation in conferences", "Participated in 2 conferences" , "Discussed 2 topics", "Everithyng went well", 2, 2, "Promote 2 reaserch papers", "Did not promote anything", "Meetings witg VeA Board", "Everything went well", null, null, "Attend three courses", null, "Dont have plans", "Participated in Java conference", null, "Planned VIRAC Christmass event",PlanStatus.plan_open);
				Plan plan3 = new Plan(emp5, year3, 1, 1, "Participation in conferences", "Participated in 2 conferences" , "Discussed 2 topics", "Everithyng went well", 2, 2, "Promote 2 reaserch papers", "Did not promote anything", "Meetings witg VeA Board", "Everything went well", null, null, "Attend three courses", null, "Dont have plans", "Participated in Java conference", null, "Planned VIRAC Christmass event",PlanStatus.done_frozen);
				Plan plan4 = new Plan(emp5, year2, 1, 1, "Participation in conferences", "Participated in 2 conferences" , "Discussed 2 topics", "Everithyng went well", 2, 2, "Promote 2 reaserch papers", "Did not promote anything", "Meetings witg VeA Board", "Everything went well", null, null, "Attend three courses", null, "Dont have plans", "Participated in Java conference", null, "Planned VIRAC Christmass event",PlanStatus.done_frozen);
				
				
				planRepo.saveAll(Arrays.asList(plan1, plan2, plan3, plan4));
				
				//CourseTable DONE
				Course c1 = new Course("Programmēšanas inženierija I", 4, "rudens", "ITF");
				Course c2 = new Course("Programmēšanas inženierija II", 4, "pavasara", "ITF");
				Course c3 = new Course("Datu bāzes I", 2, "rudens", "ITF");
				
				courseRepo.saveAll((Arrays.asList(c1,c2,c3)));
				
				//CoursePlanTable DONE
				CoursePlan cp1 = new CoursePlan(plan1, c3, null);
				CoursePlan cp2 = new CoursePlan(plan1, c1, null);
				CoursePlan cp3 = new CoursePlan(plan1, c2, "Novadīts veiksmīgi");
				CoursePlan cp4 = new CoursePlan(plan2, c2, "Novadīts veiksmīgi");
				
				courPlanRepo.saveAll((Arrays.asList(cp1,cp2,cp3,cp4)));
				
				//JournalTable DONE
				Journal journ1 = new Journal("IET Software");
				Journal journ2 = new Journal("Empirical Software Engineering");
				Journal journ3 = new Journal("ACM Computing Surveys");
				
				jourRepo.saveAll((Arrays.asList(journ1,journ2,journ3)));
				
				//ScientificArticles DONE 
				ScientificArticles scArt1 = new ScientificArticles("Impact of Co-Occurrences of Code Smells and Design Patterns on Internal Code Quality Attributes",
						"Sania Imran, Irum Inayat, Maya Daneva", journ1);
				
				ScientificArticles scArt2 = new ScientificArticles("On detection latencies of network intrusion detectors – discussion and application", 
						"Tommaso Puccetti & Andrea Ceccarelli ", journ2); 

				scientArtRepo.saveAll((Arrays.asList(scArt1,scArt2)));
				
				//ArticlePlanTable DONE
				ArticlePlan artPlan1 = new ArticlePlan(plan1, scArt2, "Izstrāde pabeigta 2024.gadā beigās", "https://arxiv.org/abs/2402.09082");
				ArticlePlan artPlan2 = new ArticlePlan(plan1, scArt1, "Izstrāde pabeigta 2024.gada sākumā", "https://doi.org/10.1049/sfw2/5579438");
				
				artPlanRepo.saveAll(Arrays.asList(artPlan1,artPlan2));
				
				//StudentWorkTable DONE
				StudentWork stw1 = new StudentWork("VIRAC personāla plānošanas sistēmas izstrāde", "Annija", "Stateviča", Degree.pirma_cikla);
				StudentWork stw2 = new StudentWork("Gaisabalons.lv klientu vadības sistēmas klientu reģistrācijas un automatizētas apziņošanas moduļa prototipa projektēšana un izstrāde",
						"Viktors", "Lačinovs", Degree.bakalaurs);
				
				studWorkRepo.saveAll(Arrays.asList(stw1,stw2));
				
				//WorkPlanTable DONE
				WorkPlan wp1 = new WorkPlan(stw1, plan1, null);
				WorkPlan wp2 = new WorkPlan(stw2, plan1, "Aizstāvēts uz 9 ballēm");
				
				workPlanRepo.saveAll(Arrays.asList(wp1,wp2));
				
				//ProjectManagementTable DONE
		        LocalDate sd1 = LocalDate.of(2024,03,28);
		        LocalDate ed1 = LocalDate.of(2025,10,3);
		        LocalDate sd2 = LocalDate.of(2024,01,6);
		        LocalDate ed2 = LocalDate.of(2025,11,13);
				
				ProjectManagement projMan1 = new ProjectManagement(emp1, sd1, ed1);
				ProjectManagement projMan2 = new ProjectManagement(emp2, sd1, ed1);
				
				projMangRepo.save(projMan1);
				projMangRepo.save(projMan2);
				
				//ProjectTable DONE
				Project proj1 = new Project("Project 1", 54862, projMan1, sd1, ed1, "P1");
				Project proj2 = new Project("Project 2", 43512, projMan2, sd2, ed2, "P2");
				
				projRepo.saveAll(Arrays.asList(proj1,proj2));
				
				//ProjectPlanTable
				ProjectPlan projPlan1 = new ProjectPlan(plan1, proj1, "Task1, Task 2", null);
				ProjectPlan projPlan2 = new ProjectPlan(plan1, proj2, null, "Done");
				ProjectPlan projPlan3 = new ProjectPlan(plan3, proj2, null, "Done");
				ProjectPlan projPlan4 = new ProjectPlan(plan4, proj1, null, "Done");
				ProjectPlan projPlan5 = new ProjectPlan(plan4, proj2, null, "Done");
				
				projPlanRepo.saveAll(Arrays.asList(projPlan1,projPlan2,projPlan3,projPlan4, projPlan5));
				
			}
		};
	}

}

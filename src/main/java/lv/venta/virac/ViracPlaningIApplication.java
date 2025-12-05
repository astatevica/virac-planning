package lv.venta.virac;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lv.venta.virac.model.ArticlePlan;
import lv.venta.virac.model.Course;
import lv.venta.virac.model.CoursePlan;
import lv.venta.virac.model.Employee;
import lv.venta.virac.model.Journal;
import lv.venta.virac.model.ProjectManagement;
import lv.venta.virac.model.ScientificArticles;
import lv.venta.virac.model.StudentWork;
import lv.venta.virac.model.ViracDepartment;
import lv.venta.virac.model.WorkPlan;
import lv.venta.virac.model.Year;
import lv.venta.virac.model.enums.Degree;
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
			IViracDepartmentRepo viracDepRepo, IWorkPlanRepo workPlanRepo)
	{
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				
				//YearTable DONE
				Year year1 = new Year(2023);
				Year year2 = new Year(2024);
				Year year3 = new Year(2025);
				
				yearRepo.saveAll((Arrays.asList(year1, year2, year3)));
		
				//ViracDepartmentTable DONE
				ViracDepartment dep1 = new ViracDepartment("Electronics and Satellite Technology");
				ViracDepartment dep2 = new ViracDepartment("Astronomy and Astrophysics");
				ViracDepartment dep3 = new ViracDepartment("Engineering & Technical Operations group(ETO/ETOG)");
				
				viracDepRepo.saveAll((Arrays.asList(dep1, dep2, dep3)));
				
				//EmployeeTable DONE
				Employee emp1 = new Employee("Karina", "Šķirmante", dep1 , "Pētnieks");
				Employee emp2 = new Employee("Juris", "Kalvāns", dep2 , "Nodaļas vadītājs, Vadošais pētnieks");
				Employee emp3 = new Employee("Māra", "Klausa", dep3 , "Tehniskais speciālists");
				
				emploRepo.saveAll((Arrays.asList(emp1, emp2, emp3)));
				
				//CourseTable DONE
				Course c1 = new Course("Programmēšanas inženierija I", 4, "rudens", "ITF");
				Course c2 = new Course("Programmēšanas inženierija II", 4, "pavasara", "ITF");
				Course c3 = new Course("Datu bāzes I", 2, "rudens", "ITF");
				
				courseRepo.saveAll((Arrays.asList(c1,c2,c3)));
				
				//CoursePlanTable (TO BE DONE)
				CoursePlan cp1 = new CoursePlan(null, c3, null);
				CoursePlan cp2 = new CoursePlan(null, c1, null);
				CoursePlan cp3 = new CoursePlan(null, c2, null);
				
				courPlanRepo.saveAll((Arrays.asList(cp1,cp2,cp3)));
				
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
				
				//ArticlePlanTable (TO BE DONE)
				ArticlePlan artPlan1 = new ArticlePlan(null, scArt2, "Izstrāde pabeigta 2024.gadā beigās", "https://arxiv.org/abs/2402.09082");
				ArticlePlan artPlan2 = new ArticlePlan(null, scArt1, "Izstrāde pabeigta 2024.gada sākumā", "https://doi.org/10.1049/sfw2/5579438");
				
				artPlanRepo.saveAll(Arrays.asList(artPlan1,artPlan2));
				
				//StudentWorkTable DONE
				StudentWork stw1 = new StudentWork("VIRAC personāla plānošanas sistēmas izstrāde", "Annija", "Stateviča", Degree.pirma_cikla);
				StudentWork stw2 = new StudentWork("Gaisabalons.lv klientu vadības sistēmas klientu reģistrācijas un automatizētas apziņošanas moduļa prototipa projektēšana un izstrāde",
						"Viktors", "Lačinovs", Degree.bakalaurs);
				
				studWorkRepo.saveAll(Arrays.asList(stw1,stw2));
				
				//WorkPlanTable (TO BE DONE)
				WorkPlan wp1 = new WorkPlan(stw1, null, null);
				WorkPlan wp2 = new WorkPlan(stw2, null, "Aizstāvēts uz 9 ballēm");
				
				workPlanRepo.saveAll(Arrays.asList(wp1,wp2));
				
				//ProjectManagementTable
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		        Date sd1 = sdf.parse("28/03/2024");
		        Date ed1 = sdf.parse("03/10/2025");
				
				ProjectManagement projMan1 = new ProjectManagement(emp1, null, sd1, ed1);
				
				projMangRepo.save(projMan1);
				
				
			}
		};
	}

}

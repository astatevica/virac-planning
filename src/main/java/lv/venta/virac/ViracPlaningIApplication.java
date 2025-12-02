package lv.venta.virac;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lv.venta.virac.model.Year;
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
				Year year1 = new Year(2023);
				Year year2 = new Year(2024);
				Year year3 = new Year(2025);
				
				yearRepo.save(year1);
				yearRepo.save(year2);
				yearRepo.save(year3);
//				
//				Professor p1 = new Professor("Karina", "Šķirmante", Degree.magistra);
//				Professor p2 = new Professor("Kārlis", "Immers", Degree.magistra);
//				Professor p3 = new Professor("Vija", "Vagale", Degree.doktora);
//				
//				//garais pieraksts
//				/*
//				profRepo.save(p1);
//				profRepo.save(p2);
//				profRepo.save(p3);
//				*/
//				//īsais pieraksts
//				profRepo.saveAll((Arrays.asList(p1, p2, p3)));
//			
//				Course c1 = new Course("Programmatūras Inženierija I", 6, p1);
//				Course c2 = new Course("Datubāzes II", 3, p3);
//				Course c3 = new Course("Web tehnoloģijas", 6, p2);
//				
//				courseRepo.saveAll(Arrays.asList(c1, c2, c3));
//				
//				Grade g1 = new Grade(6, s1, c1);//Konstantīns nopelnīja 6 ProgInz I
//				Grade g2 = new Grade(9, s1, c2);//Konstantīns nopelnīja 9 Datubāzēs II
//				Grade g3 = new Grade(3, s2, c1);//Guna nopelnīja 3 ProgInz I
//				Grade g4 = new Grade(8, s2, c2);//Guna nopelnīja 8 Datubāzēs II
//				Grade g5 = new Grade(10, s2, c3);//Guna nopelnīja 10 Webteh
//				Grade g6 = new Grade(7, s3, c2);//Sintija nopelnīja 7 Datubāzēs II
//				Grade g7 = new Grade(5, s3, c3);//Sintija nopelnīja 5 Webteh
//				grRepo.saveAll(Arrays.asList(g1, g2, g3, g4, g5, g6, g7));
			}
		};
	}

}

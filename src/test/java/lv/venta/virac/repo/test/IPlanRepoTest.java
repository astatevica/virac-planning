package lv.venta.virac.repo.test;

import org.junit.jupiter.api.Test;

import lv.venta.virac.model.Employee;

public class IPlanRepoTest {

	@Test
	void testFindByEmployeeOrderByYearDesc() {

	    Employee employee = employeeRepo.save(new Employee());

	    Year y2024 = new Year();
	    y2024.setYearNumber(2024);
	    yearRepo.save(y2024);

	    Year y2025 = new Year();
	    y2025.setYearNumber(2025);
	    yearRepo.save(y2025);

	    Plan p1 = new Plan();
	    p1.setEmployee(employee);
	    p1.setYear(y2024);

	    Plan p2 = new Plan();
	    p2.setEmployee(employee);
	    p2.setYear(y2025);

	    planRepo.save(p1);
	    planRepo.save(p2);

	    ArrayList<Plan> result =
	            planRepo.findByEmployee_IdEmployeeOrderByYear_YearNumberDesc(
	                    employee.getIdEmployee()
	            );

	    assertEquals(2, result.size());
	    assertEquals(2025, result.get(0).getYear().getYearNumber());
	}
}

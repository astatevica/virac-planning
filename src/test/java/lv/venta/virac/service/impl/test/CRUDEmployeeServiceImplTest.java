package lv.venta.virac.service.impl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Optional;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;


import jakarta.persistence.EntityManager;
import lv.venta.virac.model.Employee;
import lv.venta.virac.model.ViracDepartment;
import lv.venta.virac.repo.IEmployeeRepo;
import lv.venta.virac.repo.IViracDepartmentRepo;
import lv.venta.virac.service.impl.CRUDEmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CRUDEmployeeServiceImplTest {
	
	@Mock
	private IEmployeeRepo emplRepo;

	@Mock
	private IViracDepartmentRepo depRepo;

	@Mock
	private EntityManager entityManager;

	@Mock
	private Session session;

	@Mock
	private Filter filter;

	@InjectMocks
	private CRUDEmployeeServiceImpl employeeService;
	
	@Test
	void testRetrieveAll() throws Exception {

	    ArrayList<Employee> employees = new ArrayList<>();
	    employees.add(new Employee());

	    when(entityManager.unwrap(Session.class)).thenReturn(session);

	    when(session.enableFilter("deletedEmployeeFilter")).thenReturn(filter);

	    when(emplRepo.findAll()).thenReturn(employees);

	    ArrayList<Employee> result = employeeService.retrieveAll();

	    assertEquals(1, result.size());

	    verify(session).disableFilter("deletedEmployeeFilter");
	}
	
	@Test
	void testRetrieveAllEmpty() {

	    when(entityManager.unwrap(Session.class)).thenReturn(session);

	    when(session.enableFilter("deletedEmployeeFilter")).thenReturn(filter);

	    when(emplRepo.findAll()).thenReturn(new ArrayList<>());

	    assertThrows(Exception.class,() -> employeeService.retrieveAll());
	}
	
	@Test
	void testRetrieveById() throws Exception {

	    Employee employee = new Employee();

	    when(emplRepo.findById(1)).thenReturn(Optional.of(employee));

	    Employee result = employeeService.retrieveById(1);

	    assertNotNull(result);
	}
	
	@Test
	void testRetrieveByIdInvalidId() {

	    assertThrows(Exception.class,() -> employeeService.retrieveById(0));
	}
	
	@Test
	void testCreate() throws Exception {

	    ViracDepartment dep = new ViracDepartment();

	    when(emplRepo.findAll()).thenReturn(new ArrayList<>());

	    when(depRepo.findByName("IT")).thenReturn(dep);

	    employeeService.create(
	            "Janis",
	            "Berzins",
	            "IT",
	            "Programmer"
	    );

	    verify(emplRepo).save(any(Employee.class));
	}
	
	@Test
	void testCreateDuplicate() {

	    Employee existing = new Employee();

	    existing.setName("Janis");
	    existing.setSurname("Berzins");
	    existing.setDeleted(false);

	    ArrayList<Employee> employees = new ArrayList<>();

	    employees.add(existing);

	    ViracDepartment dep = new ViracDepartment();

	    when(emplRepo.findAll()).thenReturn(employees);

	    when(depRepo.findByName("IT")).thenReturn(dep);

	    assertThrows(Exception.class,
	            () -> employeeService.create(
	                    "Janis",
	                    "Berzins",
	                    "IT",
	                    "Programmer"
	            ));
	}
	
	@Test
	void testCreateDepartmentNotFound() {

	    when(emplRepo.findAll()).thenReturn(new ArrayList<>());

	    when(depRepo.findByName("IT")).thenReturn(null);

	    assertThrows(Exception.class,
	            () -> employeeService.create(
	                    "Janis",
	                    "Berzins",
	                    "IT",
	                    "Programmer"
	            ));
	}
	
	@Test
	void testUpdateById() throws Exception {

	    Employee employee = new Employee();

	    ViracDepartment dep = new ViracDepartment();

	    when(emplRepo.findById(1)).thenReturn(Optional.of(employee));

	    when(depRepo.findByName("IT")).thenReturn(dep);

	    employeeService.updateById(
	            1,
	            "Janis",
	            "Berzins",
	            "IT",
	            "Programmer"
	    );

	    assertEquals("Janis",employee.getName());

	    verify(emplRepo).save(employee);
	}
	
	@Test
	void testDeleteById() throws Exception {

	    Employee employee = new Employee();

	    when(emplRepo.findById(1)).thenReturn(Optional.of(employee));

	    employeeService.deleteById(1);

	    assertTrue(employee.isDeleted());

	    verify(emplRepo).save(employee);
	}
	
	@Test
	void testSelectAllEmployeesByDepartment()throws Exception {

	    ViracDepartment dep = new ViracDepartment();

	    ReflectionTestUtils.setField(
	            dep,
	            "idDepartment",
	            1
	    );

	    ArrayList<Employee> employees = new ArrayList<>();

	    employees.add(new Employee());

	    when(depRepo.findByName("IT")).thenReturn(dep);

	    when(emplRepo.findByViracDepartment_IdDepartment(1)).thenReturn(employees);

	    ArrayList<Employee> result = employeeService.selectAllEmployeesByDepartment("IT");

	    assertEquals(1, result.size());
	}

}

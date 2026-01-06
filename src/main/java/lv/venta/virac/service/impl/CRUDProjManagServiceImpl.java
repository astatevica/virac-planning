package lv.venta.virac.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.virac.model.Employee;
import lv.venta.virac.model.ProjectManagement;
import lv.venta.virac.repo.IEmployeeRepo;
import lv.venta.virac.repo.IProjectManagementRepo;
import lv.venta.virac.service.ICRUDProjManagService;

@Service
public class CRUDProjManagServiceImpl implements ICRUDProjManagService{

	@Autowired
	private IProjectManagementRepo managRepo;
	
	@Autowired
	private IEmployeeRepo emplRepo;
	
	@Override
	public ArrayList<ProjectManagement> retrieveAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProjectManagement retrieveById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(int id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(Employee employee, LocalDate startDate, LocalDate endDate) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateById(int id, Employee employee, LocalDate startDate, LocalDate endDate) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<ProjectManagement> selectAllProjectManagemetByEmployee(int employeeId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ProjectManagement> selectAllProjectManagemetByStartDate(LocalDate startDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ProjectManagement> selectAllProjectManagemetByEndDate(LocalDate endDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

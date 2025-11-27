package repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.ProjectManagement;

public interface IProjectManagementRepo extends CrudRepository<ProjectManagement, Integer>{

}

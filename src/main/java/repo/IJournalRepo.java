package repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.virac.model.Journal;

public interface IJournalRepo extends CrudRepository<Journal, Integer>{

}

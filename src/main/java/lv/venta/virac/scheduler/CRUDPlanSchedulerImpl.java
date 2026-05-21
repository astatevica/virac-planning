package lv.venta.virac.scheduler;

import java.util.ArrayList;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.venta.virac.repo.IYearRepo;
import lv.venta.virac.model.Year;

@Service
public class CRUDPlanSchedulerImpl implements ICRUDPlanSchedulerService{

	private IPlanScheduleRepo scheduleRepo;
	
	private IYearRepo yearRepo;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	private ModelMapper modelMapper;
	
	public CRUDPlanSchedulerImpl(IPlanScheduleRepo scheduleRepo, IYearRepo yearRepo, ModelMapper modelMapper) {
		this.scheduleRepo = scheduleRepo;
		this.yearRepo = yearRepo;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public ArrayList<PlanSchedule> retrieveAll() throws Exception {
		Session session = entityManager.unwrap(Session.class); 
        Filter filter = session.enableFilter("deletedPlanScheduleFilter");
        filter.setParameter("isDeleted", false);
        ArrayList<PlanSchedule> planSchedules = (ArrayList<PlanSchedule>) scheduleRepo.findAll();
        if (planSchedules.isEmpty()) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "There is no Plan Schedules"
        );
        session.disableFilter("deletedPlanScheduleFilter");
        return planSchedules;
	}

	@Override
	public PlanSchedule retrieveById(int id) throws Exception {
		if (id < 1) {throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid ID"
        );}
        return scheduleRepo.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Plan Schedule with the id: (" + id + ") does not exist!"
        ));
	}

	@Override
	public void deleteById(int id) throws Exception {
		PlanSchedule planSchedule = scheduleRepo.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Plan Schedule with id:"+ id +" does not exist"
        ));
    	planSchedule.setDeleted(true); // SOFT DELETE
    	scheduleRepo.save(planSchedule);  // SAVE, NOT DELETE
		
	}

	@Override
	public void create(SchedulerDTO dto) throws Exception {
		ArrayList<PlanSchedule> planSchedules = (ArrayList<PlanSchedule>) scheduleRepo.findAll();
		
        if(dto.getIdYear() == 0){
        	throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The input parameters are incorrect"
            ); 
		}
        
        Year year = yearRepo.findById(dto.getIdYear()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Year id not found"
        ));
        
        for (PlanSchedule pss : planSchedules) {
            if (pss.getYear().getIdYear()==dto.getIdYear() && pss.isDeleted( )) {
            	throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Plan Schedule with ID: " + pss.getIdPlanSchedule() + " already exists"
                );
            }
        }

        PlanSchedule ps = new PlanSchedule(year,dto.getPlannedFreezeDate(),dto.getDoneFreezeDate());
        scheduleRepo.save(ps);
		
	}

	@Override
	public void update(SchedulerDTO dto) throws Exception {
		PlanSchedule ps = scheduleRepo.findByYear_IdYear(dto.getIdYear());
    	if (ps == null) 
    		throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Plan Schedule with (year id:" + dto.getIdYear() + ") does not exist"
            );   	
    	
        ps.setPlannedFreezeDate(dto.getPlannedFreezeDate());
        ps.setDoneFreezeDate(dto.getDoneFreezeDate());
        scheduleRepo.save(ps);
		
	}

	@Override
	public SchedulerDTO getByYearId(int idYear) throws Exception {
        if (idYear < 1)throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid id"
        );  

        PlanSchedule foundPlanSchedules = scheduleRepo.findByYear_IdYear(idYear);

        if (foundPlanSchedules == null)
        	throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Plan Schedule with the idYear: (" + idYear + ") does not exist!"
            );

        SchedulerDTO dto = modelMapper.map(foundPlanSchedules, SchedulerDTO.class);

        dto.setIdYear(foundPlanSchedules.getYear().getIdYear());

        return dto;
	}

}

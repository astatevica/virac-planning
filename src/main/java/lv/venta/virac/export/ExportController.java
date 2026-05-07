package lv.venta.virac.export;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lv.venta.virac.dto.FullPlanDTO;
import lv.venta.virac.email.EmailSendingService;
import lv.venta.virac.service.ICRUDEmployeeService;
import lv.venta.virac.service.ICRUDPlanService;
import lv.venta.virac.user.Role;
import lv.venta.virac.user.User;

@RestController
@RequestMapping("/api/export")
@CrossOrigin(origins = "http://localhost:3000")
public class ExportController {
	
	private final PlanExportService exportService;
    private final ICRUDPlanService planService;
    private ICRUDEmployeeService employeeService;
	private EmailSendingService emailService;
	
	public ExportController(PlanExportService exportService, ICRUDPlanService planService, 
			ICRUDEmployeeService employeeService, EmailSendingService emailService) {
		this.exportService = exportService;
		this.planService = planService;
		this.employeeService = employeeService;
		this.emailService = emailService;
	}

    @GetMapping("/docx/{id}")
    public ResponseEntity<byte[]> exportDocx(@PathVariable int id,Authentication authentication){

    	try {
	        FullPlanDTO dto = planService.retrieveFullPlan(id);
	        
	        //Variables for verifying
	        User sessionUser = (User) authentication.getPrincipal();
		    int sessionUserId = sessionUser.getEmployee().getIdEmployee();
		    int planOwnerId = dto.getIdEmployee();
		    Role sessionUserRole = sessionUser.getRole();
		    int sessionUserDepartmentId = sessionUser.getEmployee().getViracDepartment().getIdDepartment();
		    int planOwnerDepartmentId = employeeService.retrieveById(planOwnerId).getViracDepartment().getIdDepartment();
		    		    
		    //Verifying
		    if (sessionUserRole == Role.ADMIN || 
		    	(sessionUserRole == Role.USER_DEPART && sessionUserDepartmentId == planOwnerDepartmentId) || 
		    	sessionUserId == planOwnerId) {
		    	
		    	//Building file
		        byte[] file = exportService.generateDocx(dto);
		        
		        //Sending e-mail
		        emailService.sendDocxEmailNotification(System.getenv("EMAIL_USERNAME"), sessionUser.getEmail(), 
		        		"Jūsu plāns ir sagatavots!", 
		        		"Sveicināti!\n\nJūsu personīgais plāns ir gatavs!\n\nFailu atradīsiet pielikumā!\n\nJauku dienu!\n\n ", 
		        		file);
		        
		        //Returning file
		        return ResponseEntity.ok()
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=VIRAC_plan.docx")
		                .contentType(MediaType.APPLICATION_OCTET_STREAM)
		                .body(file);
		        
	        }else {
	        	 return ResponseEntity.status(500).build();
	        }
	    
	    }catch(Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(500).build();
	    }
    }
}

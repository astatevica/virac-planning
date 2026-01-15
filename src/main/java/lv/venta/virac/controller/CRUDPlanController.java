package lv.venta.virac.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lv.venta.virac.service.ICRUDPlanService;

@RestController
@RequestMapping("/api/plan")
@CrossOrigin(origins = "http://localhost:3000")
public class CRUDPlanController {
	
	private ICRUDPlanService planService;
	
	public CRUDPlanController(ICRUDPlanService planService) {
		this.planService = planService;
	}

}

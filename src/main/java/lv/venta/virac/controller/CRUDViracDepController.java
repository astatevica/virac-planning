package lv.venta.virac.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.virac.model.ViracDepartment;
import lv.venta.virac.service.ICRUDViracDepService;

@Controller
@RequestMapping("/department")
public class CRUDViracDepController {
	
	@Autowired
	private ICRUDViracDepService depService;
	
	//Retrieve all
	@GetMapping("/show/all")
	public String getShowAllDepartments(Model model) {
		try {
			ArrayList<ViracDepartment> allDepartments = depService.retrieveAll();
			model.addAttribute("departments",allDepartments);
			return "departments-show-all-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
	}

}

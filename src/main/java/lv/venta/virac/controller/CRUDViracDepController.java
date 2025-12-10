package lv.venta.virac.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
			model.addAttribute("mydata",allDepartments);
			return "departments-all-page"; 
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
	}
	
	//Retrieve by id
	@GetMapping("/show/all/{id}")
	public String getShowOneDepartment(@PathVariable("id") int id, Model model) {
		try {
			ViracDepartment department = depService.retrieveById(id);
			model.addAttribute("mydata",department);
			return "departments-all-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
	}
	
	//Delete by id
	@GetMapping("/delete/{id}")
	

}

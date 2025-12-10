package lv.venta.virac.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lv.venta.virac.model.ViracDepartment;
import lv.venta.virac.service.ICRUDViracDepService;

@RestController
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
	public String getDeleteOneDepartment(@PathVariable("id") int id, Model model){
        try {
            depService.deleteById(id);
            ArrayList<ViracDepartment> allDepartments = depService.retrieveAll();
            model.addAttribute("mydata", allDepartments);
            return "departments-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }
	
	//Create 
	@GetMapping("/add")
    public String getAddDepartment(Model model) {
        model.addAttribute("department", new ViracDepartment());
        return "department-add-page";
    }


    @PostMapping("/add")
    public String postAddDepartment(@Valid ViracDepartment department, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "department-add-page";
        } else {
            try {
                depService.create(department.getName());
                return "redirect:/department/show/all";
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "error-page";
            }
        }
    }
    
    //Update by id
    @GetMapping("/update/{id}")
	public String getUpdateDepartmentById(@PathVariable(name="id") int id, Model model) {
		try
		{
			ViracDepartment depForUpdating = depService.retrieveById(id);
			model.addAttribute("department", depForUpdating);
			return "update-department";
		}
		catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "error-page";
		}
		
	}
	@PostMapping("/update/{id}")
	public String postUpdateDepartmentById(@Valid ViracDepartment department, BindingResult result,
			Model model, @PathVariable(name = "id") int id) {
		if(result.hasErrors()) {
			return "update-department";
		}
		else
		{
			try
			{
				depService.updateById(id, department.getName());
				return "redirect:/department/show/all" + id;
			}
			catch (Exception e) {
				model.addAttribute("package", e.getMessage());
				return "error-page";
			}
		}
	}

	

}

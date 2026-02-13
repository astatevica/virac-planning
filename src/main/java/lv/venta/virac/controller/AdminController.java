package lv.venta.virac.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lv.venta.virac.auth.AuthenticationService;
import lv.venta.virac.auth.dto.RegisterRequest;
import lv.venta.virac.user.CRUDUserServiceImpl;
import lv.venta.virac.user.ICRUDUserService;
import lv.venta.virac.user.User;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	private AuthenticationService authenticationService;
	private ICRUDUserService userService;
	
	public AdminController(AuthenticationService authenticationService, CRUDUserServiceImpl userService) {
		this.authenticationService = authenticationService;
		this.userService = userService;
	}

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "Only ADMIN can see this";
    }
    
    @PostMapping("/create-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest request) {
        authenticationService.createUserByAdmin(request);
        return ResponseEntity.ok("User created successfully");
    }
    
    @GetMapping("/all-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArrayList<RegisterRequest>> getAllUsers() throws Exception {

        ArrayList<User> user = userService.retrieveAll();

        ArrayList<RegisterRequest> response =
        	    new ArrayList<>(
        	        user.stream()
        	            .map(us -> new RegisterRequest(
        	            	us.getIdUser(),
        	            	us.getFirstname(),
        	            	us.getLastname(),
        	            	us.getEmail(),
        	            	us.getPassword(),
        	            	us.getRole().toString(),
        	            	us.getEmployee().getIdEmployee()
        	            ))
        	            .toList()
        	    );
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegisterRequest> getById(
            @PathVariable("id") int id) throws Exception {

        User us = userService.retrieveById(id);
        System.out.println(us);
        return ResponseEntity.ok(
            new RegisterRequest(
            		us.getIdUser(),
            		us.getFirstname(),
	            	us.getLastname(),
	            	us.getEmail(),
	            	us.getPassword(),
	            	us.getRole().toString(),
	            	us.getEmployee().getIdEmployee())
        );
    }
    
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> update(
            @PathVariable("id") int id,
            @Valid @RequestBody RegisterRequest us,
            BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        userService.updateById(id, us.getFirstname(),
            	us.getLastname(),
            	us.getEmail(),
            	us.getPassword(),
            	us.getRole().toString(),
            	us.getIdEmployee());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) throws Exception {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

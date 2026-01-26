package lv.venta.virac.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

//    private final AdminService adminService;
//
//    @GetMapping("/dashboard")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> getDashboardStats() {
//        return ResponseEntity.ok(adminService.getDashboardStats());
//    }
//
//    @PostMapping("/create-user")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
//        adminService.createUser(request);
//        return ResponseEntity.ok("User created successfully");
//    }
//
//    @DeleteMapping("/user/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
//        adminService.deleteUser(id);
//        return ResponseEntity.ok("User deleted");
//    }
}


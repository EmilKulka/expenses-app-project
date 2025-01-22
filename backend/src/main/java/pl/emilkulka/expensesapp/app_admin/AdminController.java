package pl.emilkulka.expensesapp.app_admin;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.emilkulka.expensesapp.app_user.AppUser;
import pl.emilkulka.expensesapp.app_user.AppUserService;
import pl.emilkulka.expensesapp.common.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/admin")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:4200"})
public class AdminController {
    private final AppUserService appUserService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<AppUser>>> getAppUsers() {
        List<AppUser> appUsers = appUserService.getAllAppUsers();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("success", "Users fetched successfully", appUsers));
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteAppUserById(@PathVariable("id") UUID id) {
        appUserService.deleteAppUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
